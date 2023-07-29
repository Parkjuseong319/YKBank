package pack.about.model;

import lombok.Data;

@Data
public class AboutDto {
	private String user_id,user_name,user_email,user_level;
	
	public String getLevel() {
        switch (user_level) {
            case "silver":
                return "silver";
            case "gold":
                return "gold";
            case "platinum":
                return "platinum";
            case "diamond":
                return "diamond";
            default:
                return "unknown";
        }
    }
}
