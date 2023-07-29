package pack.signup.model;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import pack.signup.controller.LoginBean;

@Mapper
public interface LoginMapperInterface {
	//@Insert("insert into USER values(#{bean.user_no},#{bean.user_id},#{bean.user_passwd},#{bean.user_name},#{bean.user_email},#{bean.user_jumin},#{bean.user_tel},#{bean.user_gen},#{bean.user_zipcode},#{bean.user_addr},#{bean.user_addr2}")
	//@Insert("insert into USER values(#{user_id},#{user_passwd},#{user_name},#{user_email},#{user_jumin},#{user_tel},#{user_gen},#{user_zipcode},#{user_addr},#{user_addr2},now(),now(),'logining',now(),'silver'")
	@Insert("insert into user (user_id,user_passwd, user_name, user_email, user_jumin,user_tel, user_gen,user_zipcode,user_addr,user_addr2 ,user_regdate,last_login_dt,user_level) "
			+ "values (#{user_id},#{user_passwd},#{user_name},#{user_email},#{user_jumin},#{user_tel},#{user_gen},#{user_zipcode},#{user_addr},#{user_addr2},now(),now(),'silver')")
	int insertData(LoginBean bean);
	
	// 로그인 성공시 로그인한 사용자의 log 기록 insert
	@Insert("insert into user_login_history (history_ip_addr, history_login_dt, user_no) VALUES (#{param1},now(),#{param2})")
	int insertLogData(String history_ip_addr, int user_no);
	
	@Select("select * from user where user_id=#{param1}")
	LoginDto userLogin(String user_id);
	
	@Select("select user_no,user_id,user_passwd,user_name, user_level from user where user_id=#{param1} AND user_passwd=#{param2}")
	LoginDto userLoginCheck(String user_id, String user_passwd);
	
	@Select("select * from user where user_id=#{param1}")
	List<LoginDto> userDoubleCheck(String user_id);
	
	@Select("select * from user where user_name=#{param1} and user_jumin=#{param2}")
	List<LoginDto> idFindCheck(String user_name, String user_jumin);
	
	@Select("select * from user where user_id=#{param1} and user_name=#{param2} and user_jumin=#{param3}")
	List<LoginDto> passwdFindCheck(String user_id, String user_name, String user_jumin);
}
