package pack.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.admin.model.AdminManageDataDao;
import pack.admin.model.AdminManageDto;
import pack.customer.business.CustomerBean;
import pack.customer.model.CustomerDao;
import pack.customer.model.CustomerDto;
import pack.paging.PagingProcess;

@Controller
public class ManageController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	CustomerDao customerDao;
	@Autowired
	private AdminManageDataDao manageDataDao;
	
	
	// 회원 관리 홈페이지로 이동
	@GetMapping("/adminManager")
	public String adminManager(HttpSession session, Model model,PagingProcess pagingClass ,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		int spage = 0;

		try {
			spage = page;
		} catch (Exception e) {
			spage=1;
		}
		if(page <= 0) spage =1;
		
		//pageing 처리
		pagingClass.setTot(manageDataDao.totalCnt());
		ArrayList<AdminManageDto> slist = (ArrayList<AdminManageDto>)manageDataDao.getUserData();
		ArrayList<AdminManageDto> result = pagingClass.getListdata(slist,spage);
		
		model.addAttribute("datas",result);
		model.addAttribute("pagesu",pagingClass.getPageSu());
		model.addAttribute("page",spage);
		
		return "html.admin/userlist";
	}
	
	// 회원 상세 보기
	@GetMapping("/signupmanager")
	public String signupmanager(Model model,@RequestParam("user_no")String user_no) {
		AdminManageDto dto = manageDataDao.getDetailImfo(user_no);
		model.addAttribute("data",dto);
		return "html.admin/signupmanager";
	}
	
	// 메일 답장
	@GetMapping("upDelpageAdmin")
	public String mailAdmincheck(Model model,CustomerBean bean,
			@RequestParam("customer_email_no")String customer_email_no) {

			CustomerDto dto = (CustomerDto)customerDao.updateSelect(customer_email_no);

			model.addAttribute("data",dto);
			return "html.admin/repMail";
	}
	// 유저 검색
	@GetMapping("userSearch")
	public String usersearch(Model model,@RequestParam("searchValue")String searchValue) {
		List<AdminManageDto> list = manageDataDao.usersearch(searchValue);
		
		model.addAttribute("datas", list);
		
		return "html.admin/userlist";
	}
}
