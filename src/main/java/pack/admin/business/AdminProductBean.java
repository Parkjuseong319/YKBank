package pack.admin.business;

import lombok.Data;

@Data
public class AdminProductBean {
	private String fin_prdt_code, kor_co_nm, fin_prdt_type, fin_prdt_nm, created_user_id, created_dt, updated_user_id,
			updated_dt, prodt_detail;
	private int max_limit, expired;
	private double basic_rate;
	private String searchValue;
}
