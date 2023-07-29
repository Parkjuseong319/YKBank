package pack.customer.model;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pack.customer.business.CustomerBean;

@Repository
public class CustomerDao {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CustomerDataInterface dataInterface;
	
	//메일 자료 입력
	public boolean customerInsert(CustomerBean bean) {
		try {
			dataInterface.CustomerMailInsert(bean);
			
			return true;
		} catch (Exception e) {
			logger.info("customerInsert fail : " + e.getMessage());
			return false;
		}		
	}
	
	//faq 리스트 뽑아오기
	public List<CustomerDto> CustomerFaqList(String customer_faq_category){
		List<CustomerDto> flist = dataInterface.CustomerFaqList(customer_faq_category);
		
		return flist;
	}
	
	//자료 선택 
	public List<CustomerDto> customerMailSelect(){
		List<CustomerDto> flist = dataInterface.customerMailSelect();
		
		return flist;
	}
	
	//선택 자료 상세보기
	public CustomerDto updateSelect(String customer_email_no) {
		CustomerDto dto = dataInterface.updateSelect(customer_email_no);
		
		return dto;
	}
	
	public int pageSelect() {
		int dto = dataInterface.pageSelect();
		return dto;
	}
	
	public boolean mailDelete(String customer_email_no) {
		try {
			dataInterface.mailDelete(customer_email_no);
			
			return true;
		} catch (Exception e) {
			logger.info("CustomerBean fail : " + e.getMessage());
			return false;
		}		
	}
	
	public boolean mailUpdate(CustomerBean bean) {
		try {
			dataInterface.mailUpdate(bean);
			
			return true;
		} catch (Exception e) {
			logger.info("mailDelete fail : " + e.getMessage());
			return false;
		}		
	}
	
	public int totalCnt() {
		return dataInterface.totalCnt();
	}
	
	public int currentNum() {
		return dataInterface.currentNum();
	}
	
	public List<CustomerDto> mailSearch(String searchValue){
		List<CustomerDto> list = dataInterface.mailSearch(searchValue);
		
		return list;
	}
}
