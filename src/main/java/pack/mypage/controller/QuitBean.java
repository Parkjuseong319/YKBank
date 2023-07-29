package pack.mypage.controller;

import lombok.Data;

@Data
public class QuitBean {
	private String quit_bank_name,quit_account_num, user_name;
	private String user_tel, quit_reg_date, quit_process_status;
}
