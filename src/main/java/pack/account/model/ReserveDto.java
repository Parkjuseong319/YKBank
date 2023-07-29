package pack.account.model;

import lombok.Data;

@Data
public class ReserveDto {
	private String remit_no, transaction_no, user_no, transaction_status,
	transacted_date, transaction_deadline, created_user_id, created_date;
}
