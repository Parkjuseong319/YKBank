package pack.account.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.account.model.AccountDao;
import pack.account.model.AccountDto;
import pack.account.model.TransferDao;
import pack.account.model.TransferDto;

@Controller
public class AccountController {

	@Autowired
	@Qualifier("accountDao")
	private AccountDao adao;
	
	@Autowired
	@Qualifier("transferDao")
	private TransferDao tdao;

	@GetMapping("index")
	public String main() {
		return "index";
	}

	// 계좌 조회 하는 컨트롤러
	@GetMapping("view")
	public String Aspec_viewAccount(HttpServletRequest request,HttpServletResponse response, Model model, HttpSession session) {
		String user_id = (String)session.getAttribute("user_id");
		ArrayList<AccountDto> list = adao.myAccount(user_id);
		model.addAttribute("info", list);
		return "html.account/account";
	}

	// 즉시 이체 첫 화면.
	@GetMapping("directsend1")
	public String Aspec_send1(HttpServletRequest request,HttpServletResponse response,
			Model model, HttpSession session) {
			String user_id = (String) session.getAttribute("user_id");
			ArrayList<AccountDto> list = adao.myAccount(user_id);
			model.addAttribute("infoall", list);
			List<AccountDto> blist = adao.showbank();
			model.addAttribute("bank",blist);
			return "html.account/directsend1";	
	}
	
	// 사용자 요청값을 받아오고, 비밀번호 체크 과정 필요
	@PostMapping("directsend2")
	public String Aspec_send2(HttpServletRequest request,HttpServletResponse response,AccountBean bean, Model model, @RequestParam("account_passwd") String passwd, 
			@RequestParam("selectaccount") String account_number1, @RequestParam("account_number") String account_number2,
			@RequestParam("sendmoney")String money,HttpSession session) {

		String user_id = (String)session.getAttribute("user_id");
		AccountDto list = adao.myAccountOne(user_id, account_number1);
		model.addAttribute("info", list);
		AccountDto dto1 = adao.checkaccount(account_number1);
		
		int sendmoney = Integer.parseInt(money);
		int usermoney = Integer.parseInt(list.getAccount_balance());
		if(usermoney < sendmoney) {
			model.addAttribute("text", "잔액이 부족합니다.");
			return "html.error/error";
		}
		if (dto1.getAccount_passwd().equals(passwd)) { // 계좌비밀번호 일치 확인
			AccountDto dto2 = adao.checkaccount(account_number2); // 받는 계좌번호 일치여부
			if(dto2.getAccount_number() != null) {
				if(usermoney > sendmoney) {
				model.addAttribute("infosend", dto1); // 사용자의 정보
				model.addAttribute("inforeceive", dto2); // 이체 받는 사람의 정보
				model.addAttribute("sendmoney", money);
				return "html.account/directsend2";
				}else {
					model.addAttribute("text", "잔액이 부족합니다.");
					return "html.error/error";
				}
			}else {
				model.addAttribute("text", "존재하지 않는 계좌번호 입니다.");
				return "html.error/error";
			} 
		}else {
			model.addAttribute("text", "해당 계좌 비밀번호와 일치하지 않습니다. 다시 시도 바랍니다.");
			return "html.error/error";
		}
	}
	
	// 거래 정보 추가되면서 화면에 표시해줌.
	@PostMapping("directsend3")
	public String Aspec_send3(HttpServletRequest request,HttpServletResponse response,
			TransferBean bean, Model model, HttpSession session,
			 @RequestParam("sendmoney") String sendmoney,
			 @RequestParam("give_account_number")String give_account_number,
			 @RequestParam("take_account_number") String take_account_number,@RequestParam("sendbank")String bank ) {
		String user_id = (String)session.getAttribute("user_id");
		AccountDto dto = adao.myAccountOne(user_id, give_account_number);
		String account_no = dto.getAccount_no();
		AccountDto dto2 = adao.checkaccount(take_account_number);
		bean.setTransaction_type("REMIT");
		bean.setTransaction_result_type("FAIL");
		bean.setTake_fin_co_no(bank);
		
		boolean b = tdao.transaction(bean, sendmoney, user_id,
				give_account_number, take_account_number, account_no);		// 거래정보 추가가 되면 true, 추가 실패하면 false 반환
		if(b) {
			TransferDto tdto = adao.showTransaction(user_id, bean.getTransaction_no());
			model.addAttribute("transinfo", tdto);
			model.addAttribute("take" ,dto2);
			model.addAttribute("give", dto);
			model.addAttribute("sendmoney", sendmoney);
			model.addAttribute("give_account_number", give_account_number);
			model.addAttribute("take_account_number", take_account_number);
			return "html.account/directsend3";			// 추가가 되고 클라이언트 화면에 표시할 정보들도 넘긴다.
		}else {
			model.addAttribute("text", "올바른 거래 시도가 아닙니다. 다시 시도 바랍니다.");
			return "html.error/error";
		}
	}
	
	// directsend3에서 확인 눌렀을 때 양 계좌 업데이트 시키는 method
	@PostMapping("directsend4")
	public String Aspec_send4(HttpServletRequest request,HttpServletResponse response,
			HttpSession session, TransferBean bean, Model model,
			 @RequestParam("transaction_no")String transaction_no) {
		String user_id = (String)session.getAttribute("user_id");
		// 단순히 거래 정보 읽어오는 용도로 사용
		TransferDto tdto = adao.showTransaction(user_id, transaction_no);
		
		// 입금받은 상대 계좌의 id 값 찾기
		AccountDto take_dto = adao.checkaccount(tdto.getTake_account_number());
		String take_user_id = take_dto.getUser_id();
		String take_account_no = take_dto.getAccount_no();
		
		// 이체가 되고 나서 상대방이 입금 받았다는 내역 생성
		String sendmoney = tdto.getAmount();
		String give_account_number = tdto.getGive_account_number();
		String take_account_number = tdto.getTake_account_number();
		
		int amount = Integer.parseInt(sendmoney);
		// 이체하는 메소드 반환값 저장
		boolean b1 = tdao.directsend(amount, give_account_number, take_account_number, transaction_no);
		if(b1) {		// 이체 성공시
			bean.setTransaction_type("WITHDRAW");
			bean.setTransaction_result_type("SUCCESS");
			bean.setAccount_no(take_account_no);
			// 이체 성공 후 상대 계좌기준 입금 됐다는 거래내역 생성
			boolean b2 = tdao.transaction(bean, sendmoney, take_user_id,
					take_account_number, give_account_number, take_account_no);
			if(b2) {	// 생성 완료 됐을때
				// 이체 완료와 업데이트 완료된 상태의 거래 정보 읽어오기 위해 한번더 사용
				TransferDto tdto2 = adao.showTransaction(user_id, transaction_no);
				model.addAttribute("transinfo", tdto2);
				// 보내는 사람 정보
				AccountDto adto =adao.myAccountOne(user_id, tdto.getGive_account_number());
				model.addAttribute("giveinfo", adto);
				// 받는 사람의 정보
				AccountDto adto2 = adao.myAccountOne(take_user_id, take_account_number);
				model.addAttribute("takeinfo",adto2);
				return "html.account/directsend4";	
			}else {
				model.addAttribute("text", "계좌 이체에 실패하였습니다. 다시 시도 바랍니다.");
				return "html.error/error";
			}
		}else {
			model.addAttribute("text", "계좌 이체에 실패하였습니다. 다시 시도 바랍니다.");
			return "html.error/error";	
		}
	}
}
