package pack.notification.model;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pack.admin.model.AdminManageDto;
import pack.notification.business.NotificationBean;

@Mapper
public interface NotificationDataInterface {
	
	// �������� ��ü ����Ʈ
	@Select("select * from notification")
	List<NotificationDto> nodificationSelect();

	// �������� �� ��
	@Select("select * from notification where notification_no=#{notification_no}")
	NotificationDto notificationOne(int notification_no);

	// ��ü �̺�Ʈ ����Ʈ
	@Select("select * from event")
	List<NotificationDto> eventSelect();
	
	// �̺�Ʈ �� �� (�󼼺��� �� ���� ������)
	@Select("select * from event where event_no=#{event_no}")
	NotificationDto eventOne(int event_no);
	
	@Select("select * from terms")
	List<NotificationDto> termsSelect();
	
	// ��� �� �� (�󼼺���)
	@Select("select * from terms where terms_no=#{terms_no}")
	NotificationDto termsOne(int terms_no);
	
	@Insert("insert into notification(notification_title,notification_content,notification_date) values(#{notification_title},#{notification_content},now())")
	int nodificationInsert(NotificationBean bean);
	
	@Insert("insert into event(event_title,event_content,event_date) values(#{event_title},#{event_content},now())")
	int eventInsert(NotificationBean bean);
	
	@Insert("insert into terms(terms_title,terms_content,terms_date) values(#{terms_title},#{terms_content},now())")
	int termsInsert(NotificationBean bean);
	
	
	@Update("update event set event_title=#{event_title},event_content=#{event_content} where event_no=#{event_no}")
	int eventUpdate(NotificationBean bean);
	
	
	@Update("update terms set terms_title=#{terms_title},terms_content=#{terms_content} where terms_no=#{terms_no}")
	int termsUpdate(NotificationBean bean);
	
	
	@Delete("delete from event where event_no=#{event_no}")
	int eventDelete(int event_no);

	
	@Delete("delete from terms where terms_no=#{terms_no}")
	int termsDelete(int terms_no);
	
	@Select("select * from notification where notification_title like '%${searchValue}%'")
	List<NotificationDto> notificationSearch(String searchValue);
	
	@Select("select * from event where event_title like '%${searchValue}%'")
	List<NotificationDto> eventSearch(String searchValue);
	
	@Select("select * from terms where terms_title like '%${searchValue}%'")
	List<NotificationDto> termsSearch(String searchValue);
	
}
