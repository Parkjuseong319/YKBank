package pack.signup.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pack.about.model.ChartDao;
import pack.about.model.ChartDto;
import pack.signup.model.LoginDao;
import pack.signup.model.LoginDto;



@Controller
public class LoginController {
	@Autowired
	private LoginDao loginDao;
	
	@Autowired
	private ChartDao chartDao;
	
	@GetMapping("login")
	public String login(HttpServletRequest request) {
		 HttpSession session = request.getSession();
		String referer = (String)request.getHeader("REFERER");
		session.setAttribute("urlName", referer);
		
		return "html.login/login";
	}
	
	@PostMapping("login")
	public String postLogin(HttpSession session,
			@RequestParam("id")String id,@RequestParam("passwd")String passwd) {
		LoginDto loginDto = loginDao.userLogin(id);

		if(loginDto != null) {
			String retName = loginDto.getUser_name();
			if(retName.equals(id)) {//사용자가 입력한 이름과 jikwon table의 이름이 같은 경우
				session.setAttribute("id", retName);
			}
		}
		return "redirect:/login";//로그인 성공하면 핵심로직 메소드 처리 결과 확인
	}
	
	@GetMapping("passwordfind")
	public String passwordfind(Model model) {
		return "html.login/passwordfind";
	}
	@GetMapping("signup")
	public String signup() {
		return "html.login/signup";
	}
	@GetMapping("userfind")
	public String userfind() {
		return "html.login/userfind";
	}
	@GetMapping("certificate")
	public String certificate() {
		return "html.login/certificate";
	}
	@GetMapping("certificate2")
	public String certificate2() {
		return "html.login/certificate2";
	}
	@PostMapping("logininsert")
	public String insetProcess(LoginBean bean) {
		boolean b = loginDao.insert(bean);
		
		if(b) {
			//return "list";  이러면안대 forward다 클라이언트주소가안바껴 pk error야 꼭 redirect해야되
			ChartDto chartDto = chartDao.chartSelect();
			try {
			    if(chartDto == null) {
			        String col1 = "chart_join";
			        String para1 = "1";
			        chartDao.chartInsert(col1, para1);
			    } else {
			        String col1 = "chart_join";
			        String para1 = "1";
			        chartDao.chartplusUpdate(col1, para1);     
			    }           
			} catch (Exception e) {
			    System.out.println("error" + e);
			}		
			
			
			return "redirect:login";
			//return "redirect:list"; 둘다 사용해도 상관없음
		}else {
			return "redirect:http://localhost/err";
		}
	}
	//중복체크
	@GetMapping("check-userid-duplication")
	@ResponseBody
	public Map<String, Object> doubleCheck(@RequestParam("user_id")String user_id){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> data = null;
		for(LoginDto s:loginDao.userDoubleCheck(user_id)) {
			data = new HashMap<String, String>();
			data.put("customer_faq_question", s.getUser_id());
			list.add(data);
		}
		
		Map<String, Object> faqList = new HashMap<String, Object>();
		
		faqList.put("datas", list);
		
		return faqList;
	}
	
	
	@PostMapping("/myidCheck")
	@ResponseBody
	public Map<String, Object> idFindCheck(@RequestParam("user_name")String user_name, 
			@RequestParam("user_jumin")String user_jumin,Model model){
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> data = null;
		for(LoginDto s:loginDao.idFindCheck(user_name, user_jumin)) {
			data = new HashMap<String, String>();
			data.put("user_id", s.getUser_id());
			list.add(data);
		}
		
		Map<String, Object> idchecklist = new HashMap<String, Object>();
		
		idchecklist.put("data", list);
		
		return idchecklist;
	}
	
	@PostMapping("/mypasswordCheck")
	@ResponseBody
	public Map<String, Object> passwdFindCheck(@RequestParam("user_id") String user_id,
	                                           @RequestParam("user_name") String user_name,
	                                           @RequestParam("user_jumin") String user_jumin,
	                                           HttpServletResponse response,
	                                 	      HttpServletRequest request) {
		
	    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	    Map<String, String> data = null;
	    
	    List<LoginDto> dto = loginDao.passwdFindCheck(user_id, user_name, user_jumin);
	    
	    if(dto.size() > 0) {
	    	HttpSession session = request.getSession();
            session.setMaxInactiveInterval(600);
            session.setAttribute("user_id",dto.get(0).getUser_id()); 
            session.setAttribute("user_name", dto.get(0).getUser_name());
            session.setAttribute("user_no", dto.get(0).getUser_no());
            session.setAttribute("user_level",dto.get(0).getUser_level());
	    }
	    
	    
	    for(LoginDto s:dto) {
	        data = new HashMap<String, String>();
	        data.put("user_id", s.getUser_id());
	        data.put("user_passwd", s.getUser_passwd()); // 비밀번호 값을 함께 전달합니다.
	        list.add(data);
	    }
	    
	    Map<String, Object> idchecklist = new HashMap<String, Object>();
	    idchecklist.put("data", list);

	    return idchecklist;
	}
}
