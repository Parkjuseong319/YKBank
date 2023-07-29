package pack.customer.model;

import lombok.Data;

@Data
public class CustomerDto {
	
	private String customer_email_no,customer_email_title,customer_email_content,	customer_email_name,
					customer_email_password,customer_email,	customer_email_tel,
					customer_email_agree, customer_email_accept, customer_email_date;
	
	
	private String customer_faq_category,customer_faq_question,customer_faq_answer;
}
