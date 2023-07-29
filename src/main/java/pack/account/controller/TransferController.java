package pack.account.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pack.account.model.AccountDto;
import pack.account.model.TransferDao;
import pack.account.model.TransferDto;


@Controller
public class TransferController {
	
	@Autowired
	private TransferDao dao;
	
	@PostMapping("trans")
	@ResponseBody
	public Map<String, Object> showtransaction(HttpSession session, @RequestParam(value="account_no")String account_no){
		String user_id = (String) session.getAttribute("user_id");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> data = null;
		
		for (TransferDto i:dao.showTransaction(user_id, account_no)) {
			data = new HashMap<String, String>();
			data.put("account_no", i.getAccount_no());
			data.put("user_no", i.getUser_no());
			data.put("transaction_no", i.getTransaction_no());
			data.put("transaction_type", i.getTransaction_type());
			data.put("transaction_result_type", i.getTransaction_result_type());
			data.put("amount", i.getAmount());
			data.put("take_account_number", i.getTake_account_number());
			data.put("take_fin_co_no", i.getTake_fin_co_no());
			data.put("give_account_number", i.getGive_account_number());
			data.put("transacted_dt", i.getTransacted_dt());
			data.put("transaction_status", i.getTransaction_status());
			data.put("created_user_id", i.getCreated_user_id());
			data.put("created_date", i.getCreated_date());
			data.put("user_no", i.getUser_no());
			data.put("transaction_balance", Long.toString(i.getTransaction_balance()));
			list.add(data);
		}

		Map<String, Object> translist = new HashMap<String, Object>();
		translist.put("transaction", list);
		return translist;
	}
	
}
