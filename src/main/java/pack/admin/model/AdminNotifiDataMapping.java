package pack.admin.model;


import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pack.notification.model.NotificationDto;

@Mapper
public interface AdminNotifiDataMapping {
	/* 공지 사항 자료 삽입 & 수정 & 삭제 */
	
	// 모든 공지사항 정보
	@Select("select * from notification")
	List<NotificationDto> getAllNotification();
	
	// 하나의 공지사항에 대한 정보
	@Select("select * from notification where notification_no=#{notification_no}")
	NotificationDto notificationOne(int notification_no);
	
	// 공지 사항 수정
	@Update("update notification set notification_title=#{notification_title},notification_content=#{notification_content} where notification_no=#{notification_no}")
	int updateNotification(AdminNotifiBean bean);
	
	// 공지 사항 삭제
	@Delete("delete from notification where notification_no=#{notification_no}")
	int deleteNotification(int notification_no);

}
