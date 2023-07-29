package pack.about.model;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ChartMapper {
	
	@Select("SELECT * FROM chart WHERE DATE_FORMAT(chart_date, '%Y%m') BETWEEN #{param1} AND #{param2}")
	List<ChartDto> chartList(String oneYearbefore,String nowYear);
	
	@Select("select * from chart WHERE DATE_FORMAT(chart_date, '%Y%m%d') = DATE_FORMAT(NOW(), '%Y%m%d')")
	ChartDto chartSelect();
	
	@Insert("insert into chart (${param1}, chart_date) values (#{param2}, now())")
	int chartInsert(@Param("param1")String param1, String param2);
	
	@Update("UPDATE chart SET ${param1} = ${param1} + #{param2} WHERE DATE_FORMAT(chart_date, '%Y%m%d') = DATE_FORMAT(NOW(), '%Y%m%d')")
	int chartplusUpdate(@Param("param1") String param1,String param2);

	@Update("UPDATE chart SET ${param1} = ${param1} - #{param2} WHERE DATE_FORMAT(chart_date, '%Y%m%d') = DATE_FORMAT(NOW(), '%Y%m%d')")
	int chartminUpdate(@Param("param1") String param1,String param2);

}
