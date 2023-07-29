package pack.admin.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pack.admin.business.AdminFaqBean;

@Repository
public class AdminFaqDao {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AdminFaqDataMapping faqDataMapping;
	
	
	
	public boolean adminFaqInsert(AdminFaqBean bean) {
		try {
			faqDataMapping.adminFaqInsert(bean);
			return true;
		} catch (Exception e) {
			logger.info("customerInsert fail : " + e.getMessage());
			return false;
		}	
	}
}
