package pack.about.controller;

import java.util.ArrayList;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.about.model.AboutDao;
import pack.about.model.AboutDto;


@Controller
public class AboutController {
	@Autowired
	private AboutDao aboutDao;
	

//	@GetMapping("adminManager")
//	public String adminManager(Model model) {
//		ArrayList<AboutDto>list = (ArrayList<AboutDto>)aboutDao.selectList();
//		model.addAttribute("datas",list);
//		
//		return "html.about/adminManager";
//	}

	@GetMapping("aboutintromain")
	public String aboutintromain() {
		
		return "html.about/aboutintromain";
	}
	
	@GetMapping("update")//여긴 redirect하면안대 forwarding해야되
	public String update(Model model, @RequestParam("user_id")String user_id) {
		AboutDto dto = (AboutDto)aboutDao.getData(user_id);
		model.addAttribute("data",dto);
		return "html.about/levelupdate";
	}
	
	@PostMapping("update")
	public String updateOk(AboutBean bean) {
		boolean b = aboutDao.update(bean);
		if(b) {
			return "redirect:http://localhost/adminManager";
		}else {
			return "redirect:http://localhost/error";
		}
	}
}
