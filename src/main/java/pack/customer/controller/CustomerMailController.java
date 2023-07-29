package pack.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import pack.customer.business.CustomerBean;
import pack.customer.model.CustomerDao;
import pack.customer.model.CustomerDto;

@Controller
public class CustomerMailController {

	@Autowired
	private CustomerDao customerDao;	
	
	@GetMapping("/mailconsult")
	public String mailconsult(Model model) {
		return "html.customer/mailconsult";
	}
	
	@GetMapping("/mailpassword")
	public String mailpassword(Model model, @RequestParam("customer_email_no")String customer_email_no) {
		model.addAttribute("data",customer_email_no);
		return "html.customer/mailpassword";
	}
	
	@GetMapping("/maildelete")
	public String mailDelete(Model model, @RequestParam("customer_email_no")String customer_email_no) {
	
		boolean b = customerDao.mailDelete(customer_email_no);
		if(b) {
			List<CustomerDto> slist= customerDao.customerMailSelect();
			model.addAttribute("datas",slist);
			return "html.customer/checkmail";
		}else {
			return "redirect:/error.html";
		}
	}
	
	@PostMapping("/mailupdate")
	public String mailUpdate(Model model, CustomerBean bean) {
		boolean b = customerDao.mailUpdate(bean);
		if(b) {
			List<CustomerDto> slist= customerDao.customerMailSelect();
			
			model.addAttribute("datas",slist);
			return "html.customer/checkmail";

		}

		return "html.customer/checkmail";
	}
	
	//유저 메일 확인
	@PostMapping("/upDelpage1")
	public String mailPasscheck(Model model,CustomerBean bean,
								@RequestParam("customer_email_name")String customer_email_name,
								@RequestParam("customer_email_password")String customer_email_password,
								@RequestParam("customer_email_no")String customer_email_no) {
		CustomerDto dto = (CustomerDto)customerDao.updateSelect(customer_email_no);
		
		if((dto.getCustomer_email_name().equals(bean.getCustomer_email_name()) ) &&
				dto.getCustomer_email_password().equals(bean.getCustomer_email_password())) {

			model.addAttribute("data",dto);
			return "html.customer/updatemail";
		}else {
		    
			 model.addAttribute("showModalVar1", "fail");
//			 return "redirect:/mailpassword?customer_email_no=" + customer_email_no;
			 return "html.customer/mailpassword";
		}
	}
	
	//유저 메일 검색 
	@GetMapping("mailSearch")
	public String mailSearch(@RequestParam("searchValue")String searchValue, Model model) {
		List<CustomerDto> list = customerDao.mailSearch(searchValue);
		model.addAttribute("datas", list);
		return "html.customer/checkmail";
	}
}
