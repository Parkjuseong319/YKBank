package pack.notification.model;


import lombok.Data;

@Data
public class NotificationDto {
	private String notification_no, notification_title, notification_content, notification_date;
	private String event_no, event_title, event_content, event_date;
	private String terms_no, terms_title, terms_content, terms_date;
	
	private String searchValue;

}
