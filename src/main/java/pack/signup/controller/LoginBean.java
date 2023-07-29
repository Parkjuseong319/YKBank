package pack.signup.controller;

import lombok.Data;

@Data
public class LoginBean {
	private int user_no;
	private String user_id,user_passwd,user_name,user_email,user_jumin;
	private String user_tel,user_gen,user_zipcode,user_addr,user_addr2;
	private String user_regdate,user_moddate,user_status,user_level;
}
