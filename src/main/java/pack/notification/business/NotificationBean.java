package pack.notification.business;


import lombok.Data;

@Data
public class NotificationBean {
	private String notification_no, notification_title, notification_content;
	private String event_no, event_title, event_content;
	private String terms_no, terms_title, terms_content;

}
