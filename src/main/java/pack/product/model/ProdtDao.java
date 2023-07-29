package pack.product.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pack.product.controller.ProdtBean;


@Repository
public class ProdtDao {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProdtMappingInter mappingInter;

	//전체 상품 목록 호출
	public List<ProdtDto> selectProdtAll() {
		List<ProdtDto> list = mappingInter.selectProdtAll();
		return list;
	}

	//대출 제외 최신상품 호출
	public ProdtDto selectProdtRec() {
		ProdtDto list = mappingInter.selectProdtRec();
		return list;
	}
	
	//대출 최신상품
	public ProdtDto selectLoanRec() {
		ProdtDto dto = mappingInter.selectLoanRec();
		return dto;
	}
	
	//입출금 신상품
	public ProdtDto selectAccountRec() {
		ProdtDto list = mappingInter.selectAccountRec();
		return list;
	}
	
	//적금 신상품들
	public List<ProdtDto> selectSavingRec() {
		List<ProdtDto> list = mappingInter.selectSavingRec();
		return list;
	}
	
	//대출 신상품(3개)
	public List<ProdtDto> selectLoanRecA(){
		List<ProdtDto> list = mappingInter.selectloanRecA();
		return list;
	}
	
	//상품 검색
	public List<ProdtDto> getProdtSearch(ProdtBean search){
		List<ProdtDto> slist = mappingInter.getProdtSearch(search);
		logger.info("datas : " + slist.size());
		return slist;
	}

	//상품 상세 정보
	public ProdtDto detailProdt(String code) {
		ProdtDto dto = mappingInter.selectProdt(code);
		return dto;
	}
	
	//나의 상품 보기
	public List<TakeProdtDto> myProdt(int user_no){
		List<TakeProdtDto> dto = mappingInter.selectMyProdt(user_no);
		return dto;
	}
	
	//나의 대출 보기
		public List<TakeProdtDto> myLoan(int user_no){
			List<TakeProdtDto> dto = mappingInter.selectMyLoan(user_no);
			return dto;
		}
	
	//상품 가입
	public boolean applyProdt(ProdtBean bean, int user_no) {
		try {
			//상품테이블 저장
			int re = mappingInter.applyProdt(bean.getFin_prdt_code(), bean.getAccount_number(), user_no,
					bean.getRate());
			//계좌 테이블 저장
			int re2 = mappingInter.applyAccount(bean.getFin_prdt_code(), bean.getAccount_number(),
					bean.getAccount_type(), bean.getAccount_passwd(), user_no);
			if(re > 0 && re2 > 0) { //두 메소드가 성공하면
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			logger.info("applyProdt 실패 : " + e.getMessage());
			return false;
		}
	}
	
	//대출
	public boolean registerLoan(ProdtBean bean, int user_no) {
		try {
			if (bean.getAccount_number() != null) {
			//상품테이블 저장(insert)
				int re = mappingInter.registLoan(bean.getFin_prdt_code(), bean.getAccount_number(), user_no,
						bean.getAmount(), bean.getRate(), bean.getExpired());
			//고객 계좌로 대출금 이체(update)
			int re2 = mappingInter.transactionLoan(bean.getAmount(), bean.getAccount_number());
			if(re > 0 && re2 > 0) {
				return true;
			}else {
				return false;
			}
			}else return false;
		} catch (Exception e) {
			logger.info("registerLoan 실패 : " + e.getMessage());
			return false;
		}
		
	}
	
	//새 계좌번호
	public String recntAN() {
		String accno = mappingInter.recntAN();
		return accno;
	}
	
	//대출금 받을 계좌번호
	public String loanAcc(int user_no) {
		String loanacc = mappingInter.loanAcc(user_no);
		return loanacc;
	}
	
	//계좌 해지 전 대출 상환 여부 확인
	public boolean checkLoan(String account_number) {
		int re = mappingInter.checkLoan2(account_number);
		int re2 = mappingInter.updateAcc(account_number);
		if(re > 0 && re2 >0) {
			return true;
		}else {
			return false;
		}
	}

	//상품 해지
	public void deleteProdt(String account_number) {
		mappingInter.deleteProdt(account_number); //상품해지
		mappingInter.deleteAcc(account_number); //계좌해지
	}
	
	//대출 상환
	public boolean deleteLoan(ProdtBean bean) {
		int money = mappingInter.checkLoan1(bean); // 계좌 잔액
		if (bean.getAmount() <= money) { // 계좌 잔액이 대출 상환금보다 크면
			mappingInter.backMoney(bean); //상환 진행
			mappingInter.deleteLoan(bean.getId()); //대출 상품 삭제
			return true;
		}else {
			return false; //계좌 잔액이 상환금보다 작으면
		}
	}
	
}
