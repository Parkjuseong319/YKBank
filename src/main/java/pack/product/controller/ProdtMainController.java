package pack.product.controller;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.TrueCondition;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pack.about.model.ChartDao;
import pack.about.model.ChartDto;
import pack.product.model.ProdtDao;
import pack.product.model.ProdtDto;
import pack.product.model.TakeProdtDto;


@Controller
public class ProdtMainController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProdtDao prodtDao;
	@Autowired
	private ChartDao chartDao;
	
	@GetMapping("prodtlist") //상품가입 메인상품 페이지
	public String prdtList(Model model) {
		ProdtDto dto1 = prodtDao.selectProdtRec(); //대출 제외 최신상품
		ProdtDto dto2 = prodtDao.selectAccountRec(); //입출금 신상품
		ArrayList<ProdtDto> list = (ArrayList<ProdtDto>) prodtDao.selectSavingRec(); //적금 신상품들
		model.addAttribute("data", dto1);
		model.addAttribute("data2", dto2);
		model.addAttribute("datas", list);
		logger.info("list : " + list.size());
		return "html.product/prodtlist";
	}
	
	@GetMapping("allprdtlist") //전체 상품 보기
	public String allPrdtList(Model model) {
		ArrayList<ProdtDto> list = (ArrayList<ProdtDto>) prodtDao.selectProdtAll();
		model.addAttribute("datas", list);
		return "html.product/allprdtlist";
	}
	
	@GetMapping("prodtdetail") //상품 상세 정보(관리자), 금융 상품 코드 가져옴
	public String prdtDetail(Model model, HttpSession session, @RequestParam("fin_prdt_code") String fin_prdt_code) {
		ProdtDto dto = prodtDao.detailProdt(fin_prdt_code);
		model.addAttribute("data", dto);
		logger.info("fin_prdt_code : " + fin_prdt_code);
		return "html.product/prodtdetail";
	}
	
	@GetMapping("myprodtview") //가입한 상품 정보
	public String Aspec_myPrdtView(HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response) {
		ArrayList<TakeProdtDto> list = (ArrayList<TakeProdtDto>) prodtDao.myProdt((int) session.getAttribute("user_no"));
		logger.info("prodtlist  : " + list.size());
		model.addAttribute("datas", list);
		return "html.product/myprodtview";
	}
	
	@GetMapping("myloan") //나의 대출 내역
	public String Aspec_myLoan(HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response) {
		ArrayList<TakeProdtDto> list = (ArrayList<TakeProdtDto>) prodtDao.myLoan((int) session.getAttribute("user_no"));
		logger.info("loanlist  : " + list.size());
		model.addAttribute("datas", list);
		return "html.product/myloan";
	}
	
	
	@GetMapping("search") //검색
	public String searchProcess(ProdtBean search, Model model) {
		ArrayList<ProdtDto> slist = (ArrayList<ProdtDto>) prodtDao.getProdtSearch(search);
		model.addAttribute("datas", slist);
		return "html.product/prodtsearchlist";
	}
	
	@GetMapping("loanlist") //대출 상품 페이지
	public String loanListProc(Model model) {
		ProdtDto dto = prodtDao.selectLoanRec(); //메인 신상품 1개
		ArrayList<ProdtDto> list = (ArrayList<ProdtDto>) prodtDao.selectLoanRecA(); //신상품 3개
		model.addAttribute("rdata", dto);
		model.addAttribute("datas", list);
		return "html.product/loanlist";
	}
	
	@GetMapping("introprodt") //상품 안내
	public String introProdt(String key) {
		return "html.product/introprodt";
	}
	
	@GetMapping("createpwd") //계좌 생성 시 비밀번호 4자리 설정
	public String Aspec_createpwd(ProdtBean bean, @RequestParam("fin_prdt_code") String fin_prdt_code, 
			HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		
		ProdtDto dto = prodtDao.detailProdt(fin_prdt_code);
		int accno = Integer.parseInt(prodtDao.recntAN()); //새 계좌번호를 만들기 위해 최근 계좌번호를 가져옴
		int userno = (int) session.getAttribute("user_no"); //로그인 세션에서 고객번호 가져옴
		
		ChartDto chartDto = chartDao.chartSelect();
		try {
		    if(chartDto == null) {
		        String col1 = "chart_saving_signup";
		        String para1 = "1";
		        chartDao.chartInsert(col1, para1);
		    } else {
		        String col1 = "chart_saving_signup";
		        String para1 = "1";
		        chartDao.chartplusUpdate(col1, para1);     
		    }           
		} catch (Exception e) {
		    System.out.println("error" + e);
		}
		

		
		
		logger.info("fin_prdt_code : " + fin_prdt_code);
		logger.info("basic rate : " + dto.getBasic_rate());
		model.addAttribute("userno", userno);
		model.addAttribute("accno", accno + 1);
		model.addAttribute("data", dto);
		return "html.product/createpwd";
	}
	
	@RequestMapping(value = "/apply", method = RequestMethod.POST) //계좌 상품 가입
	public String Aspec_quickApply( ProdtBean bean, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
	    boolean b = prodtDao.applyProdt(bean, (int)session.getAttribute("user_no")); //계좌, 상품 생성
	    logger.info("fin_prdt_code : " + bean.getFin_prdt_code());
	    logger.info("user_no : " + session.getAttribute("user_no"));
		if(b) {
	        return "html.product/apply";
		}else {
	        return "html.error/error";
	    }
	}
	
	@PostMapping("register") //대출 가입
	public String Aspec_sendRegister(ProdtBean bean, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		String acc = prodtDao.loanAcc((int) session.getAttribute("user_no")); //로그인 세션으로 대출에 사용할 계좌 호출
		bean.setAccount_number(acc);

		ChartDto chartDto = chartDao.chartSelect();
		try {
		    if(chartDto == null) {
		        String col1 = "chart_loan_signup";
		        String para1 = "1";
		        chartDao.chartInsert(col1, para1);
		    } else {
		        String col1 = "chart_loan_signup";
		        String para1 = "1";
		        chartDao.chartplusUpdate(col1, para1);     
		    }           
		} catch (Exception e) {
		    System.out.println("error" + e);
		}
		
		
		boolean b = prodtDao.registerLoan(bean, (int) session.getAttribute("user_no"));
		if(b) {
			return "html.product/register";			
		}else {
			return "html.error/loanerr"; //대출금 받을 계좌가 없을때 에러
		}
	}
	
	@GetMapping("deleteProdt") //상품 해지
	public String deleteProdt(@RequestParam("account_number") String account_number) {
		if(prodtDao.checkLoan(account_number)) {
			return "html.error/accdelerr"; //대출 미상환 에러
		}else {
		prodtDao.deleteProdt(account_number);
		return "redirect:/myprodtview";
	}
	}
	
	@GetMapping("deleteLoan") //대출 상환
	public String deleteLoan(ProdtBean bean, HttpSession session, @RequestParam("id") int id, @RequestParam("account_number") String account_number) {
		bean.setId(id);
		bean.setAccount_number(account_number);
		bean.setUser_no((int) session.getAttribute("user_no"));
		if(prodtDao.deleteLoan(bean)) {
			return "redirect:/myloan";
		}else {
			return "html.error/exchngerr"; //대출 상환금 부족 에러
		}
	}
	
}
