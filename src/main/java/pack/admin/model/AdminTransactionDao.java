package pack.admin.model;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pack.account.model.AccountInterface;

@Repository
public class AdminTransactionDao {
	
	@Autowired
	@Qualifier("adminTransactionDataMapping")
	private AdminTransactionDataMapping inter;
	
	@Autowired
	@Qualifier("accountInterface")
	private AccountInterface ainter;
	
	public ArrayList<AdminTransactionDto> showTransaction(){
		ArrayList<AdminTransactionDto> list = (ArrayList<AdminTransactionDto>)inter.showTransaction();
		return list;
	}
	
	public AdminTransactionDto oneTransaction(String remit_no) {
		AdminTransactionDto dto = inter.showOneTransaction(remit_no);
		return dto;
	}
	
	@Transactional
	public boolean insertTran(AdminTransactionDto bean) {
		String lastNo = Integer.toString(ainter.countTransaction() + 1);
		bean.setTransaction_no(lastNo);
		
		try {
			int re1 = inter.Transaction(bean);
			if(re1 > 0)
				return true;
			else return false;			
		} catch (Exception e) {
			System.out.println("insertTran error : " + e);
			return false;
		}
	}
	
	@Transactional
	public boolean upAccount(int sendmoney, String give_account_number,String take_account_number, String remit_no, String admin) {
		try {
			int re1 = ainter.sendmoney(sendmoney, give_account_number);
			int re2 = ainter.receivemoney(sendmoney, take_account_number);
			int re3 = inter.updateStatus(admin, remit_no);
			
			if(re1 > 0 & re2 > 0) {
				if(re3 >0) {
					return true;
				}
				else return false;
			}
			else return false;			
		} catch (Exception e) {
			System.out.println("upAccount error : " + e);
			return false;
		}
		
	}	
}
