package pack.about.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ChartDao {
	@Autowired
	private ChartMapper chartMapper;
	
	public List<ChartDto> chartList(String oneYearbefore,String nowYear){
		List<ChartDto> list = chartMapper.chartList(oneYearbefore, nowYear);
		return list;
	}
	
	public ChartDto chartSelect() {
		ChartDto dto = chartMapper.chartSelect();
		return dto;
	}
	
	public boolean chartInsert(String col1,String para1) {
		int dto = chartMapper.chartInsert(col1,para1);
		if(dto > 0) {
			return true;
		}
		
		return false;
	}
	
	public boolean chartplusUpdate(String col1, String para1) {
		int dto = chartMapper.chartplusUpdate(col1,para1);
		
		if(dto > 0) {
			return true;
		}
		return false;
	}
	
	public boolean chartminUpdate(String col1, String para1) {
		int dto = chartMapper.chartminUpdate(col1,para1);
		
		if(dto > 0) {
			return true;
		}
		return false;
	}
}
