package pack.notification.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pack.notification.business.NotificationBean;

@Repository
public class NotificationDao {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private NotificationDataInterface dataInterface;

	
	// �������� ��ü ����Ʈ
	public List<NotificationDto> notificationSelect(){
		List<NotificationDto> slist = dataInterface.nodificationSelect();
		
		return slist;
	}
	
	// �������� �� ��
	public NotificationDto notificationOne(int notification_no) {
		NotificationDto dto = dataInterface.notificationOne(notification_no);
		
		return dto;
	}
	
	// ��ü �̺�Ʈ ����Ʈ
	public List<NotificationDto> eventSelect(){
		List<NotificationDto> slist = dataInterface.eventSelect();
		
		return slist;
	}
	
	// �̺�Ʈ �ϳ� (�󼼺��� �� ���� ���� �뵵)
	public NotificationDto eventOne(int event_no) {
		NotificationDto dto = dataInterface.eventOne(event_no);
		
		return dto;
	}
	
	public List<NotificationDto> termsSelect(){
		List<NotificationDto> slist = dataInterface.termsSelect();
		
		return slist;
	}
	
	// ��� �ϳ� (�󼼺���)
	public NotificationDto termsOne(int terms_no) {
		NotificationDto dto=dataInterface.termsOne(terms_no);
		
		return dto;
	}
	
	public boolean nodificationInsert(NotificationBean bean) {
		try {
			dataInterface.nodificationInsert(bean);
			
			return true;
		} catch (Exception e) {
			logger.info("nodificationInsert fail : " + e.getMessage());
			return false;
		}		
	}
	
	public boolean eventInsert(NotificationBean bean) {
		try {
			dataInterface.eventInsert(bean);
			
			return true;
		} catch (Exception e) {
			logger.info("eventInsert fail : " + e.getMessage());
			return false;
		}		
	}
	
	public boolean termsInsert(NotificationBean bean) {
		try {
			dataInterface.termsInsert(bean);
			
			return true;
		} catch (Exception e) {
			logger.info("termsInsert fail : " + e.getMessage());
			return false;
		}		
	}
	
	// �̺�Ʈ ����
	public boolean eventUpdate(NotificationBean bean) {
		try {
			dataInterface.eventUpdate(bean);
			
			return true;
		} catch (Exception e) {
			logger.info("eventUpdate fail : " + e.getMessage());
			return false;
		}
	}
	
	// ��� ����
	public boolean termsUpdate(NotificationBean bean) {
		try {
			dataInterface.termsUpdate(bean);
			
			return true;
		} catch (Exception e) {
			logger.info("termsUpdate fail : " + e.getMessage());
			return false;
		}
	}
	
	
	// �̺�Ʈ ����
	public boolean eventDelete(int event_no) {
		try {
			dataInterface.eventDelete(event_no);
			return true;
		} catch (Exception e) {
			logger.info("eventDelete fail : " + e.getMessage());
			return false;
		}
	}

	// ��� ����
	public boolean termsDelete(int terms_no) {
		try {
			dataInterface.termsDelete(terms_no);
			return true;
		} catch (Exception e) {
			logger.info("termsDelete fail : " + e.getMessage());
			return false;
		}
	}
	
	//유저 검색
	public List<NotificationDto> notificationSearch(String searchValue){
		List<NotificationDto> list = dataInterface.notificationSearch(searchValue);
		return list;
	}
	
	//유저 검색
	public List<NotificationDto> eventSearch(String searchValue){
		List<NotificationDto> list = dataInterface.eventSearch(searchValue);
		return list;
	}
	
	//유저 검색
	public List<NotificationDto> termsSearch(String searchValue){
		List<NotificationDto> list = dataInterface.termsSearch(searchValue);
		return list;
	}
}
