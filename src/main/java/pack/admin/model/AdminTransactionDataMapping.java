package pack.admin.model;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface AdminTransactionDataMapping {
	
	// 전체 정보 확인
	@Select("select * from account a join transaction b on a.account_no=b.account_no join take_transaction c on b.transaction_no=c.transaction_no join user d on c.user_no=d.user_no order by transaction_deadline desc")
	List<AdminTransactionDto> showTransaction();
	
	// 하나의 정보만 확인
	@Select("select * from account a join transaction b on a.account_no=b.account_no join take_transaction c on b.transaction_no=c.transaction_no join user d on c.user_no=d.user_no where remit_no=#{param1}")
	AdminTransactionDto showOneTransaction(String remit_no);
	
	// 예약이체 실행시켰을 때 계좌 잔금 업데이트
	@Update("update account set account_balance=#{param1} where account_no=#{param2}")
	int updateAccount(String account_balance, String account_no);
	
	// 예약이체 성공적으로 진행 됐을때  실행
	@Update("update take_transaction set transaction_status='REMIT_COMPLETE', created_user_id=#{param1}, created_date=now() where remit_no=#{param2}")
	int updateStatus(String admin, String remit_no);
	
	@Insert("insert into transaction values(#{transaction_no}, #{account_no}, #{transaction_type}, #{transaction_result_type}, #{amount}, #{transaction_balance}, #{take_account_number}, #{take_fin_co_no}, #{give_account_number}, now())")
	int Transaction(AdminTransactionDto bean);
	
}
