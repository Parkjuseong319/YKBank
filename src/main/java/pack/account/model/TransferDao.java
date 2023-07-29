package pack.account.model;

import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pack.account.controller.TransferBean;

@Component
public class TransferDao {
	@Autowired
	private AccountInterface inter;

	// transaction table에 값을 추가하기 위해 실행된다.
	@Transactional
	public boolean transaction(TransferBean bean, String sendmoney, String user_id, String give_account_number,
			String take_account_number, String account_no) {
		String lastNo = Integer.toString(inter.countTransaction() + 1);
		bean.setTransaction_no(lastNo); // transaction_no가 1 증가해서 bean 값에 들어간다.
		bean.setTake_account_number(take_account_number);
		bean.setGive_account_number(give_account_number);
		bean.setAccount_no(account_no);
		
		// 이체 후 잔액 계산해서 넣음
		AccountDto dto = inter.showmyAccountOne(user_id, give_account_number);
		long send = Long.parseLong(sendmoney);
		long transaction_balance = Long.parseLong(dto.getAccount_balance()) - send;
		bean.setTransaction_balance(Long.toString(transaction_balance)); // 값 계산해서 넣어준다.
		bean.setAmount(send);
		
		try {
			int re = inter.transaction(bean);
			if (re > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			System.out.println("transactino error" + e.getMessage());
			return false;
		}
	}

	// 즉시 이체 후 업데이트 하기 위한 메소드
	@Transactional
	public boolean directsend(int amount, String give_account_number, 
			String take_account_number, String transaction_no) {
		try {
			int re1 = inter.sendmoney(amount, give_account_number);
			int re2 = inter.receivemoney(amount, take_account_number);
			int re3 = inter.updateResult(transaction_no);
			
			if (re1 > 0 & re3 > 0) {
				if (re2 > 0) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
			
		} catch (Exception e) {
			System.out.println("directsend error " + e);
			return false;
		}
	}
	
	public ArrayList<TransferDto> showTransaction(String user_id, String account_no){
		ArrayList<TransferDto> list = (ArrayList<TransferDto>)inter.transactionCheck(user_id, account_no);
		return list;
	}
}
