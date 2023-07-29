package pack.admin.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pack.account.model.AccountDto;

@Mapper
public interface AdminAccountDataMapping {
	
	// 사용자의 계좌 정보 불러오기
	@Select("select * from account as acc join user as u on acc.user_no=u.user_no join finprodt on prdt_code=fin_prdt_code where u.user_no=#{user_no} and fin_prdt_type in (10,20)")
	List<AccountDto> getAccountByUser(int user_no);
	
	// 사용자 계좌 상태 업데이트
	@Update("update account set account_status=#{param1} where account_no=#{param2}")
	int upAccountStatus(String account_status,int account_no);
	
	// 사용자 계좌 상세 기록 불러오기...
	//@Select("select * from transaction as t join account as a on t.account_no=a.account_no join user as u on a.user_no=u.user_no where t.account_no=#{account_no} order by transacted_dt desc")
	@Select("select * from transaction as t join account as a on t.take_account_number=a.account_number join user as u on u.user_no=a.user_no where t.account_no=#{account_no} order by transacted_dt desc")
	List<TransactionDto> getAccountDetail(int account_no);
	
	// 계좌번호 & 잔액 & 가입일
	@Select("select account_number, account_balance,account_reg_date from account where account_no=#{account_no}")
	TransactionDto getAccountInfo(int account_no);
	
	// 출금 횟수 & 총 출금 금액
	@Select("select count(*) as cnt ,sum(amount) as hap from transaction where transaction_type='REMIT' and account_no=#{account_no}")
	TransactionDto getSendMoney(int account_no);
	
	// 총 입금 횟수 & 총 입금 금액
	@Select("select count(*) as cnt ,sum(amount) as hap from transaction where transaction_type='WITHDRAW' and account_no=#{account_no}")
	TransactionDto getTotalMoney(int account_no);
}
