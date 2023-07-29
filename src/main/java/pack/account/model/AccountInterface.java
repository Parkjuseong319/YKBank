package pack.account.model;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pack.account.controller.AccountBean;
import pack.account.controller.ReserveBean;
import pack.account.controller.TransferBean;


@Mapper
public interface AccountInterface {
	
	@Select("select * from finprodt d join account a on d.fin_prdt_code=a.prdt_code join user b on a.user_no=b.user_no join bank on fin_co_no=bank_code where user_id=#{param1}")
	List<AccountDto> showmyAccount(String user_id);
	
	@Select("select * from account a join user b on a.user_no=b.user_no join bank on fin_co_no=bank_code where user_id=#{param1} and account_number=#{param2}")
	AccountDto showmyAccountOne(String user_id, String account_number);
	
	@Select("select * from account a join user b on a.user_no=b.user_no join bank on fin_co_no=bank_code where account_number=#{param1}")
	AccountDto checkaccount(String account_number);
	
	// 거래 횟수 추가
	@Insert("insert into transaction values(#{transaction_no}, #{account_no}, #{transaction_type}, #{transaction_result_type}, #{amount}, #{transaction_balance}, #{take_account_number}, #{take_fin_co_no}, #{give_account_number}, now())")
	int transaction(TransferBean bean);
	
	// 이체가 완료되고 거래내역 성공됐다고 수정
	@Update("update transaction set transaction_result_type='SUCCESS' where transaction_no=#{transaction_no}")
	int updateResult(String transaction_no);
	
	// transaction_no 계산을 위한 sql
	@Select("SELECT transaction_no FROM transaction ORDER BY transaction_no DESC LIMIT 1")
	int countTransaction();
	
	// 거래결과를 웹에 띄우기 위해 사용함
	@Select("select * from transaction a join account b on a.account_no=b.account_no join user c on b.user_no=c.user_no where user_id=#{param1} and transaction_no=#{param2}")
	TransferDto readTransfer(String user_id, String transation_no);
	
	// 보내는 사람의 통장 잔고 업데이트
	@Update("update account a set a.account_balance=(a.account_balance-#{param1}) where a.account_number=#{param2}")
	int sendmoney(int sendmoney, String give_account_number);
	
	// 받는 사람의 통장 잔고 업데이트
	@Update("update account a set a.account_balance=(a.account_balance + #{param1}) where a.account_number=#{param2}")
	int receivemoney(int sendmoney, String take_account_number);
	
	// 은행 종류 보기 위해 사용
	@Select("select * from bank")
	List<AccountDto> bankAll();
	
	// 예약 이체 테이블 개수 구하기
	@Select("SELECT remit_no FROM take_transaction ORDER BY remit_no DESC LIMIT 1")
	int take_transaction_no();
	
	// 예약이체 등록
	@Insert("insert into take_transaction values(#{remit_no}, #{transaction_no}, #{user_no}, 'REMIT_REQ', now(), #{transaction_deadline}, null, null)")
	int signupReserve(ReserveBean bean);
	
	// 조회창에서 거래 내역을 보기 위해 사용
	@Select("select * from transaction a join account b on a.account_no=b.account_no join user c on b.user_no=c.user_no where c.user_id=#{param1} and b.account_no=#{param2} order by transaction_no desc")
	List<TransferDto> transactionCheck(String user_id, String account_no);
}
