package pack.product.controller;

import lombok.Data;

@Data
public class ProdtBean {
	private String fin_prdt_code, kor_co_nm, fin_prdt_type, fin_prdt_nm, max_limit, created_user_id,
	created_dt, updated_user_id, updated_dt, prodt_detail;
	private String search;
	private String req_status, proc_status, fin_apply_dt, 
	 fin_reg_dt, fin_end_dt;
	private String fin_co_no, account_status, account_type, account_passwd, 
		account_balance, account_reg_date, accout_un_reg_date, account_number;
	private int amount, expired, user_no, id;
	private double rate, basic_rate;
}
