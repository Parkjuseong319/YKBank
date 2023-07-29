package pack.signup.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import pack.signup.model.LoginDao;
import pack.signup.model.LoginDto;

@Controller
public class loginSuccessController {
	@Autowired
	private LoginDao loginDao; 
	
	@PostMapping("loginSuccess")
	   public String loginSuccess(LoginBean bean,String user_id, String user_passwd,HttpServletResponse response,
	      HttpServletRequest request, Model model) {


	      try {
		      LoginDto dto = loginDao.userLoginCheck(user_id, user_passwd);
		      
		      String user_name = dto.getUser_name();
		      String user_level = dto.getUser_level();
		      int user_no = dto.getUser_no();
		      
		      String ip_addr=request.getRemoteAddr();	    	  
	    	  
	    	  
	         if (dto.getUser_id().equals(bean.getUser_id()) && dto.getUser_passwd().equals(bean.getUser_passwd())) {
	            HttpSession session = request.getSession();
	             session.setMaxInactiveInterval(600);
	             session.setAttribute("user_id",user_id); 
	             session.setAttribute("user_name", user_name);
	             session.setAttribute("user_no", user_no);
	             session.setAttribute("user_level",user_level);
	             loginDao.insertLogData(ip_addr, user_no);
	             String beforePage = (String)session.getAttribute("urlName");
	             if(beforePage.equals("http://localhost/logout")) {
	            	 return "index";
	             }
	             return "redirect:" + beforePage;
	            
	         }else {
	        	 return "html.login/login";
	         }
	         
	      } catch (Exception e) {
	    	  model.addAttribute("showModalVar", "fail");
	    	  return "html.login/login";
	      }
	   }
	
	@GetMapping("logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		session.removeAttribute("user_id");
		session.removeAttribute("user_name");
		session.removeAttribute("user_no");
		session.removeAttribute("user_level");
		return "index";
	}
}
