package pack.customer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pack.customer.model.CustomerDao;
import pack.customer.model.CustomerDto;

@Controller
public class CustomerFaqController {

	@Autowired
	private CustomerDao customerDao;
	
	@GetMapping("/faq")
	public String faq(Model model, String customer_faq_category,
			HttpServletRequest request, HttpServletResponse response) {
		
		if(customer_faq_category == null) {
			customer_faq_category = "조회/출금";
		}

		List<CustomerDto> slist= customerDao.CustomerFaqList(customer_faq_category);
		model.addAttribute("datas",slist);
		return "html.customer/faq";
	}
	
	@PostMapping("/faqlist")
	@ResponseBody
	public Map<String, Object> customerProcess(@RequestParam("category")String customer_faq_category){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> data = null;
		for(CustomerDto s:customerDao.CustomerFaqList(customer_faq_category)) {
			data = new HashMap<String, String>();
			data.put("customer_faq_question", s.getCustomer_faq_question());
			data.put("customer_faq_answer", s.getCustomer_faq_answer());
			list.add(data);
		}
		
		Map<String, Object> faqList = new HashMap<String, Object>();
		
		faqList.put("datas", list);
		
		return faqList;
	}
}
