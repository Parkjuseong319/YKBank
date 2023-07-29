package pack.mypage.model;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pack.mypage.controller.QuitBean;
import pack.mypage.controller.UserBean;

@Mapper
public interface DataMappingInterface {
	// 회원 정보 수정을 위해 화면에 기존 정보를 출력하기 위해 db에서 정보 읽어오기
	// session에 등록된 회원의 아이디를 이용
	@Select("select user_no,user_id, user_name, user_passwd, user_email, user_tel, user_zipcode, user_addr, user_addr2,user_level, last_login_dt, user_regdate from user where user_no=#{user_no}")
	UserDto selectUser(int user_no);
	
	// 비밀번호 변경 전 아이디 & 이름 & 주민등록번호의 일치 여부 확인
	@Select("select * from user where user_id=#{user_id} and user_name=#{user_name} and user_jumin=#{user_jumin}")
	UserDto chkPwdInfo(UserBean bean);
	
	// 비밀번호 변경
	@Update("update user set user_passwd=#{user_passwd} where user_id=#{user_id}")
	int updatePwd(UserBean bean);
	
	// 수정한 회원 정보 db에 update
	@Update("update user set user_email=#{user_email}, user_tel=#{user_tel}, user_zipcode=#{user_zipcode}, user_addr=#{user_addr}, user_addr2=#{user_addr2}, user_moddate=now() where user_id=#{user_id}")
	int updateUserInfo(UserBean bean);
	
	// 회원 로그 전체 개수
	@Select("select count(*) from user_login_history where user_no=#{param}")
	int getUserIpCnt(int user_no);
	
	// 회원 로그 정보 받아오기
	@Select("select history_ip_addr, history_login_dt, user_no from user_login_history where user_no=#{user_no}")
	List<UserIpDto> getUserIpList(int user_no); 
	
	// 회원 계좌 금액 가져가기 ( account 테이블에서 )
	@Select("select ifnull(sum(account_balance),0) from account where user_no=#{user_no}")
	long getUserBalance(int user_no);
	
	// 회원이 사용하고 있는 상품의 종류 
	@Select("select count(distinct(prdt_code)) from account where user_no=#{user_no}")
	int getPrdtCnt(int user_no);
	
	// 회원 탈퇴
	@Delete("delete from user where user_no=#{user_no}")
	int delUser(int user_no);
	
	// 페이징
    @Select("select count(*) from user_login_history where user_no=#{user_no}")
	int totalCnt(int user_no);
	
	@Insert("insert into quit_user values (#{quit_bank_name},#{quit_account_num},#{user_name},#{user_tel},now(),'신청')")
	int insertQuitInfo(QuitBean bean); 
}
