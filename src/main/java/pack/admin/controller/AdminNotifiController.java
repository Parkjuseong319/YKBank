package pack.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.admin.model.AdminNotifiBean;
import pack.admin.model.AdminNotifiDataDao;
import pack.notification.model.NotificationDto;

@Controller
public class AdminNotifiController {
	@Autowired
	private AdminNotifiDataDao dataDao;
	
	// 자료 수정 버튼을 누른 경우 
	@GetMapping("notificationupdate")
	public String goNotifiUpdateForm(Model model,@RequestParam("no")int notification_no) {
		NotificationDto dto= dataDao.notificationOne(notification_no);
		
		model.addAttribute("notification",dto);
		
		return "html.notification/updateNotification";
	}
	
	// 공지사항 수정 정보를 받아 정보 업데이트 
	@PostMapping("updateProcess")
	public String updateProcess(Model model,AdminNotifiBean bean) {
		boolean b=dataDao.updateNotification(bean);
		
		NotificationDto dto= dataDao.notificationOne(bean.getNotification_no());
		
		// 수정 완료 모달을 띄우기 위해 결과를 model에 추가
		model.addAttribute("result",b);
		// detail로 보낼 때 ( 정보의 출력을 위해)
		model.addAttribute("notification",dto);
		return "html.notification/notificationDetail";
		
		// 공지사항 전체 정보 db에서 읽어와 모델에 추가해주기
//		return "html.notification/notification";
	}
	
	// 공지사항 삭제
	@GetMapping("notificationdelete")
	public String deleteProcess(@RequestParam("no")int notification_no,Model model) {
		boolean b=dataDao.deleteNotification(notification_no);
		List<NotificationDto> list = dataDao.getAllNotification();
		
		model.addAttribute("result",b);
		model.addAttribute("datas",list);
				
		
		return "html.notification/notification";
	}
	
	
	
	
	
}
