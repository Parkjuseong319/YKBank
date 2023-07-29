package pack.admin.model;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import pack.admin.business.AdminFaqBean;


@Mapper
public interface AdminFaqDataMapping {
	
	@Insert("insert into customer_faq(customer_faq_category,customer_faq_question, customer_faq_answer)"
			+ " values(#{customer_faq_category},#{customer_faq_question},#{customer_faq_answer})")
	 int adminFaqInsert(AdminFaqBean bean);
}
