package pack.product.model;


import lombok.Data;

@Data
public class ProdtDto {
	//판매 상품
	private String fin_prdt_code, kor_co_nm, fin_prdt_type, fin_prdt_nm, max_limit, created_user_id,
		created_dt, updated_user_id, updated_dt, prodt_detail;
	private double basic_rate;
	private int expired;
}