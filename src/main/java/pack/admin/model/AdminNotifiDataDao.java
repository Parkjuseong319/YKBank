package pack.admin.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pack.notification.model.NotificationDto;

@Repository
public class AdminNotifiDataDao {
/* 관리자 - 공지사항 관리 처리 */
	
	@Autowired
	private AdminNotifiDataMapping dataMapping;
	
	// 모든 공지사항 목록
	public List<NotificationDto> getAllNotification(){
		List<NotificationDto> nList = dataMapping.getAllNotification();
		
		return nList;
	}
	
	
	// 원하는 공지사항 불러오기 (하나)
	public NotificationDto notificationOne(int notification_no) {
		NotificationDto dto = dataMapping.notificationOne(notification_no);
			
		return dto;
	}
	
	// 공지사항 수정
	public boolean updateNotification(AdminNotifiBean bean) {
		int re=dataMapping.updateNotification(bean);
		if(re>0) { 	// 수정 성공
			return true;
		}else {
			return false;
		}
	}
	
	// 공지사항 삭제
	public boolean deleteNotification(int notification_no) {
		int re=dataMapping.deleteNotification(notification_no);
		
		if(re>0) {	// 삭제 성공
			return true;
		}else {
			return false;
		}
	}
}
