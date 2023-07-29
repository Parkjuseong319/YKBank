package pack.account.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pack.account.controller.ReserveBean;

@Repository
public class ReserveDao {
	
	@Autowired
	private AccountInterface inter;
	
	public boolean insertReserve(ReserveBean bean) {
		try {
			int re = inter.signupReserve(bean);
			if(re > 0) return true;
			else return false;			
		} catch (Exception e) {
			System.out.println("insertReserve error " + e);
			return false;
		}
	}
	
	public int reserveNo() {
		int count = inter.take_transaction_no();
		return count;
	}
}
