package pack.customer.controller;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.customer.business.CustomerBean;
import pack.customer.model.CustomerDao;
import pack.customer.model.CustomerDto;
import pack.paging.PagingProcess;

@Controller
public class CustomerController {
	@Autowired
	private CustomerDao customerDao;
	
	@GetMapping("/chatconsult")
	public String productmain() {
		return "html.customer/chatconsult";
	}

	@GetMapping("/checkmail")
	public String checkmail(Model model,PagingProcess pagingClass ,
			@RequestParam(value = "page", defaultValue = "1") int page) {
	
		int spage = 0;

		try {
			spage = page;
		} catch (Exception e) {
			spage=1;
		}
		if(page <= 0) spage =1;
		
		//pageing 처리
		pagingClass.setTot(customerDao.totalCnt());
		ArrayList<CustomerDto> slist = (ArrayList<CustomerDto>)customerDao.customerMailSelect();
		ArrayList<CustomerDto> result = pagingClass.getmailList(slist,spage);
		
		model.addAttribute("datas",result);
		model.addAttribute("pagesu",pagingClass.getPageSu());
		model.addAttribute("page",spage);
		
		
		
//		model.addAttribute("datas",slist);
		return "html.customer/checkmail";
	}	

	
	@PostMapping("/mailconsult")
	public String loginpage(Model model,CustomerBean bean) {
		bean.setCustomer_email_date();
		boolean b = customerDao.customerInsert(bean);
		if(b) {
			List<CustomerDto> slist= customerDao.customerMailSelect();
			model.addAttribute("datas",slist);
			return "html.customer/checkmail";
		}else {
			return "redirect:/error.html";
		}
	}
	
	
}
