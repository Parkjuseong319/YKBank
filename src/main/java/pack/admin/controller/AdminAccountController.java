package pack.admin.controller;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.account.controller.AccountBean;
import pack.account.model.AccountDto;
import pack.admin.model.AdminAccountDao;
import pack.admin.model.TransactionDto;

@Controller
public class AdminAccountController {

	@Autowired
	private AdminAccountDao accountDao;
	private Logger logger;
	// 계좌 정보를 볼 수 있는 뷰로 이동
	@GetMapping("accountInfoView")
	public String getAccountByUser(@RequestParam("no")int user_no,@RequestParam("user_name")String user_name, Model model) {
		
		
		try {
			List<AccountDto> list = accountDao.getAccountByUser(user_no);
			model.addAttribute("userAccountList",list);
			
			model.addAttribute("user_name",user_name);
			
			
			return "html.admin/userAccount";
			
		} catch (Exception e) {
			logger.info("getAccountByUser error:"+e.getMessage());
			return "redirect:/error.html";
		}
		
	}
	
	// 계좌 상태 변경
	@PostMapping("upAccountStatus")
	public String updateStatus(AccountBean bean, Model model) {
		int user_no=Integer.parseInt(bean.getUser_no());
		int account_no=Integer.parseInt(bean.getAccount_no());
		String account_status=bean.getAccount_status();
		
		System.out.println(user_no+" "+account_no+" "+account_status);
		List<AccountDto> list = accountDao.getAccountByUser(user_no);
		
		if(account_status.equals("true")) {
			model.addAttribute("userAccountList",list);
			return "html.admin/userAccount";
		}else {
			boolean b=accountDao.upAccountStatus(account_status, account_no);
			if(b) {
				list = accountDao.getAccountByUser(user_no);
				model.addAttribute("userAccountList",list);
				return "html.admin/userAccount";
			}else {
				return "redirect:/error.html";
			}
		}
	}
	
	// 사용자 계좌 기록 상세보기
	@GetMapping("showAccountDetail")
	public String showAccountHistory(Model model,@RequestParam("account_no")int account_no) {
		// 계좌 번호에 해당되는 거래 정보 모두 불러오기
		List<TransactionDto> list = accountDao.getAccountDetail(account_no);
		// 사용자 계좌 기본 정보
		TransactionDto dto=accountDao.getAccountInfo(account_no);
		TransactionDto dto2=accountDao.getSendMoney(account_no);
		TransactionDto dto3=accountDao.getTotalMoney(account_no);
		
		model.addAttribute("accountList",list);
		model.addAttribute("accountInfo",dto);
		model.addAttribute("getMoney",dto3);
		model.addAttribute("sendMoney",dto2);
		
		return "html.admin/userAccountDetail";
	}
}
