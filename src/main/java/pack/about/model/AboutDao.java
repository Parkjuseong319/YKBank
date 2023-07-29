package pack.about.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pack.about.controller.AboutBean;




@Repository
public class AboutDao {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MappingInterface interface1;
	
	//전체 회원 읽기
	public List<AboutDto>selectList() {
		List<AboutDto>list = interface1.selectList();
		return list;
	}
	// 레코드 한 개 읽기
		public AboutDto getData(String user_id) {
			AboutDto dto = interface1.selectPart(user_id);
			return dto;
	}
	//고객 등급 수정
	public boolean update(AboutBean bean) {

		try {
			int re =interface1.updateData(bean);
			if(re >0) return true; else return false;
		} catch (Exception e) {
			logger.info("insert fail :" + e.getMessage());
			return false;
		}
	}

}
