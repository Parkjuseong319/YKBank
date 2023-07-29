package pack.notification.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.admin.model.AdminManageDto;
import pack.notification.business.NotificationBean;
import pack.notification.model.NotificationDao;
import pack.notification.model.NotificationDto;

@Controller
public class NotificationController {

	@Autowired
	private NotificationDao notificationDao;
	
	
	// ===========리스트보기
	// ===========리스트보기
	// ===========리스트보기
	@GetMapping("notification")
	public String notification(Model model) {
		List<NotificationDto> slist = notificationDao.notificationSelect();
		model.addAttribute("datas",slist);
		return "html.notification/notification";
	}
	
	@GetMapping("event")
	public String event(Model model) {
		List<NotificationDto> slist = notificationDao.eventSelect();
		model.addAttribute("datas",slist);
		return "html.notification/event";
	}
	
	@GetMapping("terms")
	public String terms(Model model) {
		List<NotificationDto> slist = notificationDao.termsSelect();
		model.addAttribute("datas",slist);
		return "html.notification/terms";
	}

	
	//인서트창 이동 ==================
	//인서트창 이동 ==================
	//인서트창 이동 ==================
	@GetMapping("notificationinsert")
	public String notificationinsert(Model model) {
		return "html.notification/notificationinsert";
	}
	
	@GetMapping("eventinsert")
	public String eventinsert(Model model) {
		return "html.notification/eventinsert";
	}
	
	@GetMapping("termsinsert")
	public String termsinsert(Model model) {
		return "html.notification/termsinsert";
	}
	
	//입력 이후 본문 이동 ==================
	//입력 이후 본문 이동 ==================
	//입력 이후 본문 이동 ==================
	
	@PostMapping("notificationinsert")
	public String notificationProcess(NotificationBean bean, Model model) {
		boolean b = notificationDao.nodificationInsert(bean);
		if(b) {
			
			List<NotificationDto> slist= notificationDao.notificationSelect();
			model.addAttribute("datas",slist);
			return "html.notification/notification";
		}else {
			return "redirect:/error.html";
		}		
		
	}
	
	// 세부 내용 보기
	// 세부 내용 보기
	// 세부 내용 보기

	// 공지사항 상세보기
	@GetMapping("notificationDetail")
	public String notificationDetail(Model model,@RequestParam("no")int notification_no) {
		NotificationDto dto=notificationDao.notificationOne(notification_no);
		model.addAttribute("notification",dto);
		
		return "html.notification/notificationDetail";
	}
	
	// 이벤트 상세 보기
	@GetMapping("eventDetail")
	public String eventDetail(Model model, @RequestParam("no") int event_no) {
		NotificationDto dto = notificationDao.eventOne(event_no);
		model.addAttribute("event", dto);

		return "html.notification/eventDetail";
	}

	// 약관 상세 보기
	@GetMapping("termsDetail")
	public String termsDetail(Model model, @RequestParam("no") int terms_no) {
		NotificationDto dto = notificationDao.termsOne(terms_no);
		model.addAttribute("terms", dto);

		return "html.notification/termsDetail";
	}
	
	@PostMapping("eventinsert")
	public String eventProcess(NotificationBean bean, Model model) {
		boolean b = notificationDao.eventInsert(bean);
		if(b) {
			
			List<NotificationDto> slist= notificationDao.eventSelect();
			model.addAttribute("datas",slist);
			return "html.notification/event";
		}else {
			return "redirect:/error.html";
		}		
		
	}

	@PostMapping("termsinsert")
	public String tremsProcess(NotificationBean bean, Model model) {
		boolean b = notificationDao.termsInsert(bean);
		if(b) {
			
			List<NotificationDto> slist= notificationDao.termsSelect();
			
			model.addAttribute("datas",slist);
		
			return "html.notification/terms";
		}else {
			return "redirect:/error.html";
		}		
		
	}	

	// 내용 수정 후 상세보기로 이동 ====================
	// 내용 수정 후 상세보기로 이동 ====================
	// 내용 수정 후 상세보기로 이동 ====================

	// 이벤트 내용 수정 폼으로 이동
	@GetMapping("eventUpdate")
	public String eventUpdate(Model model, @RequestParam("no") int event_no) {
		NotificationDto dto = notificationDao.eventOne(event_no);
		model.addAttribute("event", dto);

		return "html.notification/eventUpdate";
	}

	// 이벤트 내용 수정
	@PostMapping("eventUpdate")
	public String eventUpdateProcess(Model model, NotificationBean bean) {
		boolean b = notificationDao.eventUpdate(bean);

		if (b) {
			List<NotificationDto> slist = notificationDao.eventSelect();
			model.addAttribute("datas", slist);

			return "html.notification/event";

		} else {
			return "redirect:/error.html";
		}

	}

	// 약관 수정 폼으로 이동
	@GetMapping("termsUpdate")
	public String termsUpdate(Model model, @RequestParam("no") int terms_no) {
		NotificationDto dto = notificationDao.termsOne(terms_no);
		model.addAttribute("terms", dto);

		return "html.notification/termsUpdate";
	}

	// 이벤트 내용 수정
	@PostMapping("termsUpdate")
	public String termsUpdateProcess(Model model, NotificationBean bean) {
		boolean b = notificationDao.termsUpdate(bean);

		if (b) {
			List<NotificationDto> slist = notificationDao.termsSelect();
			model.addAttribute("datas", slist);

			return "html.notification/terms";

		} else {
			return "redirect:/error.html";
		}

	}

	// 이벤트 삭제
	@GetMapping("eventDelete")
	public String eventDeleteProcess(@RequestParam("no") int event_no, Model model) {
		boolean b = notificationDao.eventDelete(event_no);

		if (b) {
			List<NotificationDto> slist = notificationDao.eventSelect();
			model.addAttribute("datas", slist);

			return "html.notification/event";
		} else {
			return "redirect:/error.html";
		}

	}

	// 약관 삭제
	@GetMapping("termsDelete")
	public String termsDeleteProcess(@RequestParam("no") int terms_no, Model model) {
		boolean b = notificationDao.termsDelete(terms_no);
		
		if (b) {
			List<NotificationDto> slist = notificationDao.termsSelect();
			model.addAttribute("datas", slist);
			
			return "html.notification/terms";
		} else {
			return "redirect:/error.html";
		}
		
	}
	
	//====================검색
	//====================검색
	//====================검색
	@GetMapping("notificationSearch")
	public String notificationSearch(Model model,@RequestParam("searchValue")String searchValue) {
		List<NotificationDto> list = notificationDao.notificationSearch(searchValue);
		
		model.addAttribute("datas", list);
		
		return "html.notification/notification";
	}
	
	@GetMapping("eventSearch")
	public String eventSearch(Model model,@RequestParam("searchValue")String searchValue) {
		List<NotificationDto> list = notificationDao.eventSearch(searchValue);
		
		model.addAttribute("datas", list);
		
		return "html.notification/event";
	}
	
	@GetMapping("termsSearch")
	public String termsSearch(Model model,@RequestParam("searchValue")String searchValue) {
		List<NotificationDto> list = notificationDao.termsSearch(searchValue);
		
		model.addAttribute("datas", list);
		
		return "html.notification/terms";
	}
}
