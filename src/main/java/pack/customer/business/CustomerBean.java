package pack.customer.business;



import java.time.LocalDate;
import lombok.Data;

@Data
public class CustomerBean {
	private String customer_email_no,customer_email_title,customer_email_content,	
	customer_email_name,customer_email_password,customer_email,	customer_email_tel,
	customer_email_agree, customer_email_accept,customer_email_date,customer_faq_category;
	
	private String searchValue;
	
	public void setCustomer_email_date() {
		LocalDate localDate = LocalDate.now();
		int year = localDate.getYear();
		int month = localDate.getMonthValue();
		int day = localDate.getDayOfMonth();
		this.customer_email_date = year + "-" + month + "-" + day;
	}
	
}
