package pack.admin.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.account.controller.TransferBean;
import pack.account.model.AccountDao;
import pack.account.model.AccountDto;
import pack.account.model.ReserveDao;
import pack.admin.model.AdminTransactionDao;
import pack.admin.model.AdminTransactionDto;

@Controller
public class TransactionController {
	
	@Autowired
	private AdminTransactionDao dao;
	
	@Autowired
	private AccountDao adao;
	
	@Autowired
	private ReserveDao rdao;
	
	@GetMapping("reservemanage")
	public String Aspec_transactionall(HttpServletRequest request,HttpServletResponse response, Model model) {
		ArrayList<AdminTransactionDto> list = dao.showTransaction();
		model.addAttribute("datas", list);
		return "/html.admin/transaction";
	}
	
	@GetMapping("reserve")
	public String Aspec_reservedetail(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("remit_no")String remit_no, Model model) {
		AdminTransactionDto dto = dao.oneTransaction(remit_no);
		model.addAttribute("reserve", dto);
		return "html.admin/transactiondetail";
	}
	
	@PostMapping("reserve")
	public String Aspec_reserveProcess(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("amount")String sendmoney,
			@RequestParam("remit_no")String remit_no, TransferBean bean,
			HttpSession session, Model model) {
		String admin = (String)session.getAttribute("user_id");
		AdminTransactionDto dto = dao.oneTransaction(remit_no);
		String give_account_number = dto.getGive_account_number();
		String take_account_number = dto.getTake_account_number();
		
		AccountDto dto1 = adao.checkaccount(take_account_number);
		String take_account_no = dto1.getAccount_no();
	
		int cou = rdao.reserveNo();
		String count = Integer.toString(cou + 1);
		String count1 = Integer.toString(cou + 2);
		
		dto.setTransaction_no(count1);
		dto.setTransaction_type("REMIT");
		boolean b2 = dao.insertTran(dto);
		
		// 이체받는 사람 입장에서 생성시 반대로 들어가야한다.
		dto.setAccount_no(take_account_no);
		dto.setTake_account_number(give_account_number);
		dto.setGive_account_number(take_account_number);
		dto.setTransaction_no(count);
		dto.setTransaction_type("WITHDRAW");
		boolean b1 = dao.insertTran(dto);
		
		int money = Integer.parseInt(sendmoney);
		boolean b3 = dao.upAccount(money , give_account_number, take_account_number, remit_no, admin);
		
		if(b1 & b2) {
			if(b3) {
				return "redirect:reservemanage";					
			}else {
				model.addAttribute("text", "계좌 이체에 실패하였습니다. 다시 시도 바랍니다.");
				return "html.error/error";
			}
		}else {
			model.addAttribute("text", "거래내역 생성에 실패하였습니다. 다시 시도 바랍니다.");
			return "html.error/error";
		}
		
	}
}
