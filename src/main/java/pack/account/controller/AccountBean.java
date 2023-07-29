package pack.account.controller;

import lombok.Data;

@Data
public class AccountBean {
	private String account_no, user_no, account_balance;
	private String user_id, user_name, fin_co_no, account_number, account_status, account_type, 
	account_passwd, account_reg_date, account_un_reg_date,
	created_user_id, created_date, bank_name;
}
