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
import org.springframework.web.bind.annotation.ResponseBody;

import pack.account.model.AccountDao;
import pack.account.model.AccountDto;
import pack.account.model.ReserveDao;
import pack.account.model.TransferDao;
import pack.account.model.TransferDto;

@Controller
public class ReserveController {
	@Autowired
	@Qualifier("accountDao")
	private AccountDao adao;

	@Autowired
	@Qualifier("transferDao")
	private TransferDao tdao;

	@Autowired
	@Qualifier("reserveDao")
	private ReserveDao rdao;

	@PostMapping("info")
	@ResponseBody
	public Map<String, Object> userinfo(HttpSession session) {
		String user_id = (String) session.getAttribute("user_id");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> data = null;

		for (AccountDto i : adao.myAccount(user_id)) {
			data = new HashMap<String, String>();
			data.put("account_no", i.getAccount_no());
			data.put("user_no", i.getUser_no());
			data.put("account_balance", i.getAccount_balance());
			data.put("user_name", i.getUser_name());
			data.put("fin_co_no", i.getFin_co_no());
			data.put("account_number", i.getAccount_number());
			data.put("account_status", i.getAccount_status());
			data.put("account_type", i.getAccount_type());
			data.put("account_passwd", i.getAccount_passwd());
			data.put("account_reg_date", i.getAccount_reg_date());
			data.put("account_un_reg_date", i.getAccount_un_reg_date());
			data.put("created_user_id", i.getCreated_user_id());
			data.put("created_date", i.getCreated_date());
			data.put("bank_name", i.getBank_name());
			data.put("fin_prdt_nm", i.getFin_prdt_nm());
			data.put("kor_co_nm", i.getKor_co_nm());
			data.put("fin_prdt_type", i.getFin_prdt_type());
			data.put("basic_rate", i.getBasic_rate());
			data.put("max_limit", i.getMax_limit());
			data.put("updated_user_id", i.getUpdated_user_id());
			data.put("updated_dt", i.getUpdated_dt());
			data.put("prodt_detail", i.getProdt_detail());
			list.add(data);
		}

		Map<String, Object> accountlist = new HashMap<String, Object>();
		accountlist.put("info", list);
		return accountlist;
	}

	// 예약 이체 첫 화면
	@GetMapping("reservesend1")
	public String Aspec_reserve(HttpServletRequest request, HttpServletResponse response,
			HttpSession session,Model model) {
		ArrayList<AccountDto> list = adao.showbank();
		model.addAttribute("bank",list);
		return "/html.account/reservesend1";
	}

	// reservesend1 에서 사용자에게 입력 값을 받아 reserve2에 보여준다.
	@PostMapping("reservesend2")
	public String Aspec_reserve1(HttpServletRequest request,HttpServletResponse response,
			Model model, HttpSession session, @RequestParam("transaction_deadline") String date,
			@RequestParam("account_number") String take_account_number,
			@RequestParam("select_account") String give_account_number, @RequestParam("sendmoney") String sendmoney,
			@RequestParam("sendbank") String bank, @RequestParam("account_passwd") String password, TransferBean bean) {
		String user_id = (String) session.getAttribute("user_id");
		AccountDto dto1 = adao.myAccountOne(user_id, give_account_number);
		String account_no = dto1.getAccount_no();
		bean.setTransaction_type("REMIT");
		bean.setTransaction_result_type("FAIL");
		bean.setTake_fin_co_no(bank);
		
		boolean b = tdao.transaction(bean, sendmoney, user_id, give_account_number, take_account_number, account_no); 
		// 거래 정보가 추가가 되면 true, 아닐시 false
		AccountDto dto2 = adao.checkaccount(give_account_number);
		if (dto2.getAccount_passwd().equals(password)) {
			AccountDto dto3 = adao.checkaccount(take_account_number);
			if (dto2.getAccount_number() != null) {
				if (b) {
					TransferDto dto4 = adao.showTransaction(user_id, account_no);
					model.addAttribute("giveinfo", dto2);
					model.addAttribute("takeinfo", dto3);
					model.addAttribute("transinfo",dto4);
					model.addAttribute("senddate", date);
					model.addAttribute("sendmoney", sendmoney);
					model.addAttribute("sendbank", bank);
					return "/html.account/reservesend2";
				} else {
					model.addAttribute("text", "예약이체에 실패하였습니다. 다시 시도 바랍니다.");
					return "/html.error/error";
				}
			} else {
				model.addAttribute("text", "존재하지 않는 계좌번호 입니다. 다시 시도 바랍니다.");
				return "/html.error/error";
			}
		}else {
			model.addAttribute("text", "계좌 비밀번호가 틀렸습니다. 다시 시도 바랍니다.");
			return "/html.error/error";
		}
	}

	// 이체하기 눌렀을 때 거래내역 생성한다.
	@PostMapping("reservesend3")
	public String Aspec_reserve3(HttpServletRequest request,HttpServletResponse response,
			Model model, HttpSession session,
			@RequestParam("sendmoney") String sendmoney, @RequestParam("select_account") String give_account_number,
			@RequestParam("account_number") String take_account_number,
			@RequestParam("transaction_no") String transaction_no,
			@RequestParam("senddate")String senddate, ReserveBean bean) {
		
		String user_id = (String) session.getAttribute("user_id");
		AccountDto dto = adao.myAccountOne(user_id, give_account_number);
		AccountDto dto2 = adao.checkaccount(take_account_number);
		String count = Integer.toString(rdao.reserveNo()+1);
		TransferDto tdto = adao.showTransaction(user_id, transaction_no);
		bean.setUser_no(dto.getUser_no());
		bean.setTransaction_status("REMIT_REQ");
		bean.setTransaction_deadline(senddate);
		bean.setTransaction_no(transaction_no);
		bean.setRemit_no(count);
		
		boolean b = rdao.insertReserve(bean);
		
		if (b) {
			model.addAttribute("transinfo", tdto);
			model.addAttribute("takeinfo", dto2);
			model.addAttribute("giveinfo", dto);
			model.addAttribute("sendmoney", sendmoney);
			model.addAttribute("give_account_number", give_account_number);
			model.addAttribute("take_account_number", take_account_number);
			return "html.account/reservesend3"; // 추가가 되고 클라이언트 화면에 표시할 정보들도 넘긴다.
		} else {
			model.addAttribute("text", "예약 이체 신청에 실패하였습니다. 다시 시도 바랍니다.");
			return "html.error/error";			
		}
	}
}
