package pack.admin.business;

import lombok.Data;

@Data
public class ManageBean {
	private String user_name, user_passwd, user_email, user_tel, user_zipcode, user_addr, user_addr2;
	private String user_id, user_jumin, user_level, user_regdate, user_no;
	private String searchValue;
}
