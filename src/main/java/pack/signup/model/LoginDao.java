package pack.signup.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import pack.signup.controller.LoginBean;

@Repository
public class LoginDao {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LoginMapperInterface mapperInterface;

	// 회원가입
	public boolean insert(LoginBean bean) {

		try {
			int re = mapperInterface.insertData(bean);
			if (re > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			logger.info("insert fail :" + e.getMessage());
			return false;
		}
	}

	// 로그인
	public LoginDto userLogin(String user_id) {
		LoginDto dto = (LoginDto) mapperInterface.userLogin(user_id);
		return dto;
	}
	
	//로그인 체크
	public LoginDto userLoginCheck(String user_id, String user_passwd) {
		LoginDto dto = (LoginDto) mapperInterface.userLoginCheck(user_id,user_passwd);
		
		return dto;	
		
	}
	
	// 로그인 시 사용자의 로그 저장
	public boolean insertLogData(String ip_addr, int user_no) {
	      try {
	         int re = mapperInterface.insertLogData(ip_addr, user_no);
	         if (re > 0)
	            return true;
	         else
	            return false;
	      } catch (Exception e) {
	         logger.info("insertlogData fail :" + e.getMessage());
	         return false;
	      }
	      
	   };
	   
	//중복체크
	public List<LoginDto> userDoubleCheck(String user_id) {
		List<LoginDto> list = mapperInterface.userDoubleCheck(user_id);
		
		return list;
	}
	
	// 로그인
	public List<LoginDto> idFindCheck(String user_name, String user_jumin) {
		List<LoginDto> list = mapperInterface.idFindCheck(user_name,user_jumin);
		
		return list;
	}
	
	// 로그인
	public List<LoginDto> passwdFindCheck(String user_id, String user_name, String user_jumin) {
		List<LoginDto> list = mapperInterface.passwdFindCheck(user_id,user_name,user_jumin);
		return list;
	}
	   
}
