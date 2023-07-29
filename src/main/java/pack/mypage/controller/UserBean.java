package pack.mypage.controller;

import lombok.Data;

@Data
public class UserBean {
	private String user_name, user_passwd, user_email, user_tel, user_zipcode, user_addr, user_addr2;
	private String user_id, user_jumin;
}
