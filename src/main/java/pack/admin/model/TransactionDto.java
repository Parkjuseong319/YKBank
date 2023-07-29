package pack.admin.model;

import lombok.Data;

@Data
public class TransactionDto {
	private long transaction_no, account_no, amount, transaction_balance;
	private String transaction_type, transaction_result_type, take_account_number, take_fin_co_no, give_account_number,transacted_dt,user_name;
	private long fin_co_no,user_no;
	private String account_number,account_status, account_type, account_passwd, prdt_code;
	private long account_balance;
	private String account_reg_date,account_un_reg_date,created_user_id,created_date;
	private long cnt, hap;
}
