package pack.account.model;

import lombok.Data;

@Data
public class TransferDto {
	private long transaction_balance;
	private String transaction_no, account_no, transaction_type, transaction_result_type, amount, take_account_number, take_fin_co_no,
	give_account_number, transacted_dt;
	private String id, user_no, transaction_status, created_user_id, created_date;
}
