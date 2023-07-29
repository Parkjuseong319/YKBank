package pack.admin.model;

import lombok.Data;

@Data
public class AdminNotifiBean {
	private String notification_title, notification_content;
	private int notification_no;
}
