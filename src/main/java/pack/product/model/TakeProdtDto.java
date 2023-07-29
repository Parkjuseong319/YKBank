package pack.product.model;

import lombok.Data;

@Data
public class TakeProdtDto {
	//상품 신청
	private String prdt_code, account_no, user_no, amount, req_status, proc_status,
		fin_apply_dt,  fin_reg_dt, fin_end_dt, fin_prdt_nm;
	private int id;
	private double rate;
	//계좌 생성
	private String fin_co_no, account_number, account_status, account_type, account_passwd, 
	account_balance, account_reg_date, account_un_reg_date, created_user_id, created_date; 
}
