package pack.customer.model;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pack.customer.business.CustomerBean;


@Mapper
public interface CustomerDataInterface {
	@Insert("insert into customer_email "
			+ " (customer_email_title,customer_email_content,"
			+ "	customer_email_name,customer_email_password,customer_email,customer_email_tel,"
			+ "	customer_email_agree, customer_email_accept,customer_email_date)"
			+ " values"
			+ " (#{customer_email_title},#{customer_email_content},"
			+ "	#{customer_email_name},#{customer_email_password},#{customer_email},#{customer_email_tel},"
			+ "	#{customer_email_agree}, #{customer_email_accept},now())")
	 int CustomerMailInsert(CustomerBean bean);
	
	@Select("select customer_email_no,customer_email_title,customer_email_content,"
			+ "	customer_email_name,customer_email_password,customer_email,customer_email_tel,"
			+ "	customer_email_agree, customer_email_accept"
			+ " from customer_email where customer_email_no = #{param1}")
	CustomerDto updateSelect(String customer_email_no);
	
	@Select("select customer_email_no,customer_email_title,customer_email_name,customer_email_date from customer_email order by customer_email_no desc")
	List<CustomerDto> customerMailSelect();
	
	@Select("select customer_faq_category,customer_faq_question,customer_faq_answer from customer_faq where customer_faq_category = #{param1}")
	List<CustomerDto> CustomerFaqList(String customer_faq_category);
	
	@Select("select count(customer_email_no) from customer_email")
	int pageSelect();
	
	@Delete("delete from customer_email where customer_email_no=#{param1}")
	int mailDelete(String customer_email_no);
	
	@Update("update customer_email set"
			+ " customer_email_title=#{customer_email_title},"
			+ " customer_email_content =#{customer_email_content},"
			+ " customer_email_password =#{customer_email_password},"
			+ " customer_email =#{customer_email},"
			+ " customer_email_tel =#{customer_email_tel}"
			+ " where customer_email_no = #{customer_email_no}")
	int mailUpdate(CustomerBean bean);
	
	@Select("select max(customer_email_no) from customer_email")
	int currentNum();
	
	@Select("select count(*) from customer_email")
	int totalCnt();
	
	//유저 검색
	@Select("select * from customer_email where customer_email_name like '%${searchValue}%'")
	List<CustomerDto> mailSearch(String searchValue);
}
