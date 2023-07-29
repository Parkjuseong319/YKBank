package pack.admin.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import pack.admin.business.ManageBean;

@Mapper
public interface AdminManageDataMappingInterface {
	
	@Select("select max(user_no) from user")
	int currentNum();
	
	@Select("select count(*) from user")
	int totalCnt();
	
	// 유저 전체 간이 정보 가져오기
	@Select("select * from user")
	List<AdminManageDto> selectUser();
	
	@Select("select * from user where user_no = ${user_no}")
	AdminManageDto detailSelectUser(String user_no);
	
	@Select("select * from user where user_name like '%${searchValue}%'")
	List<AdminManageDto> usersearch(String searchValue);
}
