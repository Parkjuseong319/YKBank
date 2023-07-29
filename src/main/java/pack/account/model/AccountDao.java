package pack.account.model;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class AccountDao {

	@Autowired
	private AccountInterface inter;

	// 조회창에서 보기 위해 정보 불러옴
	public ArrayList<AccountDto> myAccount(String user_id) {
		ArrayList<AccountDto> list = (ArrayList<AccountDto>)inter.showmyAccount(user_id);
		return list;
	}
	
	public AccountDto myAccountOne(String user_id, String account_number) {
		AccountDto dto = inter.showmyAccountOne(user_id, account_number);
		return dto;
	}

	// 입금 받는 상대의 계좌 DB에 존재 여부 확인, 출금계좌 비밀번호 체크용
	public AccountDto checkaccount(String account_number) {
		AccountDto dto = inter.checkaccount(account_number);
		return dto;
	}

	// directsend3에서 화면에 표시하기 위해 값을 불러오는 method
	public TransferDto showTransaction(String user_id, String transaction_no) {
		TransferDto dto = inter.readTransfer(user_id, transaction_no);
		return dto;
	}
	
	
	// 은행 목록
	public ArrayList<AccountDto> showbank(){
		ArrayList<AccountDto> list = (ArrayList<AccountDto>)inter.bankAll();
		return list;
	}
}
