package pack.about.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pack.about.controller.AboutBean;


@Mapper
public interface MappingInterface {
	@Select("select * from user")
	List<AboutDto> selectList();
	
	@Select("select * from user where user_id=#{user_id}")
	AboutDto selectPart(String user_id);
	
	@Update("update user set user_level=#{user_level} where user_id=#{user_id}")
	int updateData(AboutBean bean);
	
	@Select("select * from USER where user_id=#{param1}")
	List<AboutDto> userDoubleCheck(String user_id);
}
