package pack.mypage.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import pack.about.model.ChartDao;
import pack.about.model.ChartDto;
import pack.mypage.model.DataDao;
import pack.mypage.model.UserDto;
import pack.mypage.model.UserIpDto;
import pack.paging.PagingProcess;

@Controller
public class MypageController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DataDao dao;
	@Autowired
	private ChartDao chartDao;
	// 마이페이지 메인 페이지로 이동
	@GetMapping("/mymain")
	public String Aspec_mymain(HttpSession session, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		UserDto dto = dao.getUserData((Integer) session.getAttribute("user_no"));
		model.addAttribute("userInfo",dto);
		return "html.mypage/mymain";
	}
	
	// 비밀번호 수정 전 현재 비밀번호 확인 폼으로 이동
	@GetMapping("/myChkPwd")
	public String Aspec_goChgPwd(HttpSession session,HttpServletRequest request,
			HttpServletResponse response) {
		// 임시 세션 생성
		//session.setAttribute("user_id", "aa");
		
		// model에 UserBean 타입의 빈 객체를 넘겨주기
		//model.addAttribute("bean",new UserBean());
		
		return "html.mypage/chkForm";
	}
	
	
	// 본인 인증 
	@PostMapping("/chkPwdInfo")
	public String Aspec_chgPwdInfo(UserBean bean, HttpSession session, Model model,HttpServletRequest request,
			HttpServletResponse response,@RequestParam("user_passwd")String passwd) {
		int user_no=(Integer)session.getAttribute("user_no");
		UserDto dto = dao.getUserData(user_no);
		String real_passwd=dto.getUser_passwd();
		
		boolean b=false;
		
		if(real_passwd.equals(passwd)) {
			//session.removeAttribute("executed");
			return "html.mypage/chgPwdForm";
		}else {
			model.addAttribute("result",b);
			return "html.mypage/chkForm";
		}
	}
	
	// 비밀번호 변경
	@PostMapping("chgpwd")
	public String Aspec_chgpwd(UserBean bean, HttpSession session, Model model,HttpServletRequest request,
			HttpServletResponse response) {
		bean.setUser_id((String) session.getAttribute("user_id"));
		
		boolean b = dao.updatePwd(bean);
		//logger.info((String) session.getAttribute("user_id"));
		//logger.info(bean.getUser_passwd());
		
		if(b) {
			
			UserDto dto = dao.getUserData((Integer) session.getAttribute("user_no"));
			model.addAttribute("userInfo",dto);
			return "html.mypage/mymain";
		}else {
			model.addAttribute("text","비밀번호 변경 도중 오류가 발생되었습니다.");
			return "html.error/error";
		}
		
	}
	
	// 회원 정보를 수정하기 전 비밀번호로 본인 인증 수행하기 위한 폼으로 이둥
	@GetMapping("myPwdChk")
	public String Aspec_goPwdChk(HttpSession session, Model model,HttpServletRequest request,
			HttpServletResponse response) {
		return "html.mypage/chkPwd";
	}
	
	// 회원 정보를 수정하기 전 비밀번호로 본인 인증 수행 
	@PostMapping("myPwdChk")
	public String Aspec_chkPwd(@RequestParam("user_passwd")String pwd,Model model,HttpSession session,HttpServletRequest request,
			HttpServletResponse response) {
		int user_no=(Integer)session.getAttribute("user_no");
		UserDto dto = dao.getUserData(user_no);
		String real_passwd=dto.getUser_passwd();
		
		boolean b=false;
		if(real_passwd.equals(pwd)) {
			model.addAttribute("userInfo",dto);
			return "html.mypage/updateInfo";
		}else {
			model.addAttribute("result",b);
			return "html.mypage/chkPwd";
		}
	}
	
	// 회원 정보 수정창으로 이동
	@GetMapping("updateInfo")
	public String Aspec_goUpdate(HttpSession session,Model model,HttpServletRequest request,
			HttpServletResponse response) {
		UserDto dto=dao.getUserData((Integer)session.getAttribute("user_no"));
		model.addAttribute("userInfo",dto);
		
		return "html.mypage/updateInfo";
	}
	
	// 사용자가 입력한 정보를 받아 회원 정보 수정
	@PostMapping("updateUserInfo")
	public String Aspec_updateUserInfo(UserBean bean, Model model, HttpSession session,HttpServletRequest request,
			HttpServletResponse response) {
		boolean b=dao.updateUserInfo(bean);
		UserDto dto = dao.getUserData((Integer) session.getAttribute("user_no"));
		// logger.info(bean.getUser_id());
		if(b) {
			model.addAttribute("userInfo",dto);
			return "html.mypage/mymain";
		}else {
			model.addAttribute("text","회원정보 수정 도중 오류가 발생되습니다.");
			return "html.error/error";
		}
	}
	
	// 등급별 우대 혜택 보러가기
	@GetMapping("memBenefit")
	public String goMemBenefit() {
		return "html.customer/memBenefit";
	}
	
	// 회원탈퇴 시 가입 및 보유 자산 보여주는 창
	@GetMapping("quit")
	public String Aspec_quit(HttpSession session, Model model,HttpServletRequest request,
			HttpServletResponse response) {
		// 이름 & 가입일 가져가기
		int	user_no=(Integer)session.getAttribute("user_no");
		UserDto dto = dao.getUserData(user_no);
		
		// 계좌
		long balance = dao.getUserBalance(user_no);
		
		// 가입 중인 상품의 개수
		int cnt=dao.getPrdtCnt(user_no);
		
		
		logger.info("user_regdate: " + dto.getUser_regdate());
		model.addAttribute("userInfo", dto);
		model.addAttribute("account_balance", balance);
		model.addAttribute("prdtCnt",cnt);
		
		return "html.mypage/quit";
	}
		
	// 회원탈퇴 전 비밀번호 확인을 위한 폼으로 이동
	@GetMapping("chkPwdForQuit")
	public String Aspec_goChkPwdForQuitForm(Model model, HttpSession session,HttpServletRequest request,
			HttpServletResponse response) {
		//UserDto dto = dao.getUserData((Integer) session.getAttribute("user_no"));
		//model.addAttribute("user_passwd", dto.getUser_passwd());
		
		return "html.mypage/chkPwdForQuit";
	}
	
	// 회원탈퇴 전 비밀번호 확인 (사용자가 입력한 비밀번호를 가져옴)
	@PostMapping("chkPwdForQuit")
	public String Aspec_chkPwdForQuit(@RequestParam("user_passwd")String pwd,Model model,HttpSession session,HttpServletRequest request,
			HttpServletResponse response) {
		int user_no=(Integer)session.getAttribute("user_no");
		UserDto dto = dao.getUserData(user_no);
		String real_passwd=dto.getUser_passwd();
		
		boolean b=false;
		if(real_passwd.equals(pwd)) {
			b=true;
		}
		
		model.addAttribute("result",b);
		return "html.mypage/chkPwdForQuit";
	}
	
	// 회원탈퇴 (회원 정보 삭제)
	@PostMapping("quitSuccess")
	public String Aspec_quitSuccess(HttpSession session, HttpServletRequest request,
			HttpServletResponse response,QuitBean bean,Model model) {
		
		try {
			UserDto dto = dao.getUserData((Integer)session.getAttribute("user_no"));
			bean.setUser_name(dto.getUser_name());
			bean.setUser_tel(dto.getUser_tel());
			System.out.println(bean.getQuit_bank_name());
			dao.insertQuitInfo(bean);
			
			boolean b = dao.delUser((Integer) session.getAttribute("user_no"));
			
			session = request.getSession(true);
			session.removeAttribute("user_id");
			session.removeAttribute("user_name");
			session.removeAttribute("user_no");
			if (b) {
				
				ChartDto chartDto = chartDao.chartSelect();
				
				return "html.mypage/quitSuccess";
				
			} else {
				return "html.error/error";
			}
		} catch (Exception e) {
			return "html.error/quiterr";
		}
	}
	
	// 내 ip 기록 보여주기
	   @GetMapping("myLogList")
	   public String Aspec_goLogList(Model model,HttpSession session,HttpServletRequest request,
	         HttpServletResponse response,PagingProcess pagingClass,@RequestParam(value = "page", defaultValue = "1") int page) {
	      
	      int spage = 0;

	      try {
	         spage = page;
	      } catch (Exception e) {
	         spage=1;
	      }
	      if(page <= 0) spage =1;
	      
	      // 페이징 처리
		pagingClass.setTot(dao.totalCnt((Integer)session.getAttribute("user_no")));
	      ArrayList<UserIpDto> slist=(ArrayList<UserIpDto>)dao.getUserIpList((Integer)session.getAttribute("user_no"));
	      ArrayList<UserIpDto> result = pagingClass.getIpList(slist, spage);
	      
	      model.addAttribute("datas",result);
	      model.addAttribute("pagesu",pagingClass.getPageSu());
	      model.addAttribute("page",spage);
		
	      return "html.mypage/logHistory";
	   }
	
}
