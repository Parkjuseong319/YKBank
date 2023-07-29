package pack.account.controller;

import lombok.Data;

@Data
public class TransferBean {
	private String transaction_no, account_no, transaction_type, transaction_result_type, transaction_balance, take_account_number, take_fin_co_no,
	give_account_number, transacted_date;		// transaction_balance는 계산해서 bean에다가 값을 넣어준다. amount도 넣어준다.
	private Long amount;
	private String id, user_no,user_id, transaction_status, created_user_id, created_date;
}
