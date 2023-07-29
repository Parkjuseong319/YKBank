package pack.mypage.model;

import lombok.Data;

@Data
public class UserDto {
	private String user_name, user_passwd, user_email, user_tel, user_zipcode, user_addr, user_addr2;  
	private String user_id,user_jumin, user_level, last_login_dt, user_regdate;
	private int user_no;
}
