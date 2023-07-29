package pack.admin.model;

import lombok.Data;

@Data
public class AdminTransactionDto {
	private String transaction_no, transaction_type, transaction_result_type, amount, transaction_balance, take_account_number, take_fin_co_no, give_account_number, transacted_dt;
	private String remit_no, user_no, transaction_status,transacted_date, transaction_deadline, created_user_id, created_date;
	private String account_no, fin_co_no, prdt_code, account_number, account_status, account_type, account_passwd, account_balance, account_reg_date, account_un_reg_date;
	private String user_id, user_name, user_email,user_jumin, user_tel, user_gen, user_zipcode, user_addr, user_addr2, user_regdate,user_moddate,user_level;
}
