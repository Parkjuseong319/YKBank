package pack.about.model;

import lombok.Data;

@Data
public class ChartDto {
	private String chart_join,chart_withdrawal,chart_total_person,chart_total_money,chart_saving_signup;
	private String chart_loan_interest,chart_saving_interest,chart__total_signup,chart_loan_signup;
	private String chart_date;
}
