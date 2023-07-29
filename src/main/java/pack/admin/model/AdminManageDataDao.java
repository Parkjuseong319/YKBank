package pack.admin.model;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.method.annotation.ModelAndViewMethodReturnValueHandler;

@Repository
public class AdminManageDataDao {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AdminManageDataMappingInterface mappingInterface;
	
	public int totalCnt() {
		return mappingInterface.totalCnt();
	}
	
	public int currentNum() {
		return mappingInterface.currentNum();
	}
	
	// 전체 회원 간이 정보 가져오기
	public List<AdminManageDto> getUserData(){
		List<AdminManageDto> slist = mappingInterface.selectUser();
		
		return slist;
	}
	
	//상세 정보 가져오기
	public AdminManageDto getDetailImfo(String user_no) {
		AdminManageDto dto = mappingInterface.detailSelectUser(user_no);
		
		return dto;
	}
	
	//유저 검색
	public List<AdminManageDto> usersearch(String searchValue){
		List<AdminManageDto> list = mappingInterface.usersearch(searchValue);
		return list;
	}
}
