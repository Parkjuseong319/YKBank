package pack.admin.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pack.account.model.AccountDto;

@Repository
public class AdminAccountDao {
	
	@Autowired
	private AdminAccountDataMapping dataMapping;
	
	// 사용자의 계좌정보 불러오기
	public List<AccountDto> getAccountByUser(int user_no){
		List<AccountDto> list = dataMapping.getAccountByUser(user_no);
		
		return list;
	}
	
	// 사용자 상태 정보 변경
	public boolean upAccountStatus(String account_status, int account_no) {
		int re=dataMapping.upAccountStatus(account_status, account_no);
		
		if(re>0) {
			return true;
		}else {
			return false;
		}
	}
	
	// 사용자 계좌 상세 기록 불러오기
	public List<TransactionDto> getAccountDetail(int account_no){
		List<TransactionDto> list = dataMapping.getAccountDetail(account_no);
		
		return list;
				
	}
	
	// 계좌번호 & 잔액 & 가입일
	public TransactionDto getAccountInfo(int account_no) {
		TransactionDto dto = dataMapping.getAccountInfo(account_no);
		
		return dto;
	}
	
	// 총 출금 횟수 & 출금 금액 
	public TransactionDto getSendMoney(int account_no) {
		TransactionDto dto = dataMapping.getSendMoney(account_no);
		
		return dto;
	}

	// 총 입금 횟수 & 입금 금액 
	public TransactionDto getTotalMoney(int account_no) {
		TransactionDto dto = dataMapping.getTotalMoney(account_no);
		
		return dto;
	}
}
