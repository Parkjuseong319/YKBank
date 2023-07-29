package pack.product.model;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pack.product.controller.ProdtBean;


@Mapper
public interface ProdtMappingInter {
	 //금융상품 목록 호출
	@Select("select * from finprodt")
	List<ProdtDto> selectProdtAll();
	
	//최신 금융상품 호출
	@Select("select * from finprodt where created_dt=(select max(created_dt) from finprodt where fin_prdt_type in('10','20')) and fin_prdt_type in('10','20')")
	ProdtDto selectProdtRec();
	
	//최신 대출상품 호출
	@Select("select * from finprodt where created_dt=(select max(created_dt) from finprodt where fin_prdt_type in('30')) and fin_prdt_type in('30')")
	ProdtDto selectLoanRec();
	
	 //금융상품 중복 여부 확인용 or 상품 상세 정보
	@Select("select * from finprodt where fin_prdt_code=#{param1}")
	ProdtDto selectProdt(String fin_prdt_code);
	
	
	//////상품 메인페이지///////////////////
	//입출금 상품 호출
	@Select("select * from finprodt  where created_dt=(select max(created_dt) from finprodt where fin_prdt_type in('10'))")
	ProdtDto selectAccountRec();
	
	//적금 상품 호출
	@Select("select * from finprodt where fin_prdt_type in ('20') order by created_dt desc limit 2")
	List<ProdtDto> selectSavingRec();
	
	//대출 전체상품 호출
	@Select("SELECT * "
			+ "FROM finprodt "
			+ "WHERE fin_prdt_type IN ('30') "
			+ "ORDER BY created_dt DESC "
			+ "LIMIT 3")
	List<ProdtDto> selectloanRecA();
	
	//상품 검색
	@Select("SELECT * FROM finprodt WHERE fin_prdt_nm LIKE '%${search}%' OR prodt_detail LIKE '%${search}%'")
	List<ProdtDto> getProdtSearch(ProdtBean search);
	////////////////////////////////////
	
	//가입 상품 조회
	@Select("select distinct fin_prdt_nm, account.account_number, rate, account_balance, account_reg_date, fin_end_dt from account left outer join take_finprodt on account.prdt_code = take_finprodt.prdt_code left outer join finprodt on account.prdt_code = fin_prdt_code where account.user_no = #{user_no} and fin_prdt_type in ('10', '20')")
	List<TakeProdtDto> selectMyProdt(int user_no);
	
	//가입 대출 조회
	@Select("select distinct fin_prdt_nm, account_number, rate, amount, fin_reg_dt, fin_end_dt, fin_prdt_type, id from take_finprodt left outer join finprodt on prdt_code = fin_prdt_code where user_no = #{user_no} and fin_prdt_type = '30'")
	List<TakeProdtDto> selectMyLoan(int user_no);
	
	
	//상품 가입
	@Insert("INSERT INTO take_finprodt (prdt_code, account_number, user_no, "
			+ "rate, fin_reg_dt) "
			+ "VALUES (#{fin_prdt_code}, #{account_number}, "
			+ "#{user_no}, #{rate}, NOW())")
	int applyProdt(@Param("fin_prdt_code") String param1, @Param("account_number") String param2, @Param("user_no") int param3, @Param("rate") double param4);
	
	
	//계좌 생성
	@Insert("insert into account (fin_co_no, prdt_code, user_no, account_number, account_status, "
			+ "account_type, account_passwd, account_reg_date, "
			+ "created_user_id, created_date, account_balance) values('10', #{prdt_code}, #{user_no}, "
			+ "#{account_number}, 'IN_USE', #{account_type}, #{account_passwd}, now(), "
			+ "'admin', now(), 0)")
	int applyAccount(@Param("prdt_code") String param1, @Param("account_number") String param2,
			@Param("account_type") String param3, @Param("account_passwd") String param4,
			@Param("user_no") int param5);
	
	//가장 최근의 계좌번호
	@Select("select account_number from account order by account_reg_date desc limit 1")
	String recntAN();
	
	//대출 상품 가입
	@Insert("insert into take_finprodt (prdt_code, account_number, user_no, amount, fin_reg_dt, rate, fin_end_dt) values "
			+ "(#{fin_prdt_code}, #{account_number}, #{user_no}, #{amount}, now(), #{rate}, DATE_ADD(now(), INTERVAL #{expired} MONTH))")
	int registLoan(@Param("fin_prdt_code") String param1, @Param("account_number") String param2, @Param("user_no") int param3,
			@Param("amount") int param4, @Param("rate") double param5, @Param("expired") int param6);
	
	//대출금 이체할 계좌
	@Select("select account_number from account where user_no=#{user_no} and prdt_code='abc' order by account_reg_date limit 1")
	String loanAcc(int user_no);
	
	//대출금 이체
	@Update("update account set account_balance=account_balance+#{param1} where account_number = #{param2}")
	int transactionLoan(int amount, String account_number);
	
	//대출 상환
	@Delete("delete from take_finprodt where id=#{id}")
	void deleteLoan(int id);
	
	//대출금 회수
	@Update("update account set account_balance=account_balance - #{amount} where user_no=#{user_no} and account_number=#{account_number}")
	void backMoney(ProdtBean bean);
	
	//회수 가능 금액 확인
	@Select("select account_balance from account where user_no=${user_no} and account_number=#{account_number}")
	int checkLoan1(ProdtBean bean);
	
	//계좌 해지 전 대출 여부 확인
	@Select("select count(account_number) from take_finprodt where account_number=#{account_number} and fin_end_dt is not null")
	int checkLoan2(String account_number);
	
	//상품 해지
	@Delete("delete from take_finprodt where account_number=#{account_number}")
	void deleteProdt(String account_number);
	
	//계좌 거래내역 삭제
	@Delete("delete from transaction where give_account_number=#{account_number}")
	int updateAcc(String account_number);
	
	//계좌 해지
	@Delete("delete from account where account_number=#{account_number}")
	void deleteAcc(String account_number);
	
	//insert into chart (#{chart_join},chart_date) values(#{param2},now())
}
