package pack.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import pack.admin.business.AdminFaqBean;
import pack.admin.model.AdminFaqDao;
import pack.customer.model.CustomerDao;
import pack.customer.model.CustomerDto;

@Controller
public class AdminFaqController {
	@Autowired
	private AdminFaqDao adminFaqDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	@GetMapping("faqinsert")
	public String faqinsert() {
	
		return "html.admin/faqinsert";
	}
	
	@PostMapping("faqinsert")
	public String faqinsertProcess(AdminFaqBean bean, Model model) {	
		boolean b = adminFaqDao.adminFaqInsert(bean);
		String customer_faq_category = bean.getCustomer_faq_category();
		if(b) {
			if(customer_faq_category == null) {
				customer_faq_category = "조회/출금";
			}

			List<CustomerDto> slist= customerDao.CustomerFaqList(customer_faq_category);
			model.addAttribute("datas",slist);
			return "html.customer/faq";
		}else {
			return "redirect:/error.html";
		}		
	}
}
