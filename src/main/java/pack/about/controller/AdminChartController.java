package pack.about.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pack.about.model.ChartDao;
import pack.about.model.ChartDto;

@Controller
public class AdminChartController {
	@Autowired
	private ChartDao chartDao; 
	
	@GetMapping("abouthistory")
	public String aboutHistory(Model model) {
		LocalDate currentDate = LocalDate.now();
		LocalDate oneYearAgo = currentDate.minusYears(1).plusMonths(1);
		LocalDate nowYearD = currentDate.plusMonths(1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");

		String oneYearbefore = oneYearAgo.format(formatter);
		String nowYear = nowYearD.format(formatter);
		
		List<ChartDto> list1 = chartDao.chartList(oneYearbefore, nowYear);

		
		model.addAttribute("datas",list1);
		
		
		return "html.about/abouthistory";
	}
	
	   @PostMapping("/abouthistory")
	   @ResponseBody
	   public Map<String, Object> customerProcess(){
	      List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	      Map<String, String> data = null;
	   
	      LocalDate currentDate = LocalDate.now();
	      LocalDate oneYearAgo = currentDate.minusYears(1);
	      LocalDate nowYearD = currentDate.plusMonths(1);
	      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");

	      String oneYearbefore = oneYearAgo.format(formatter);
	      String nowYear = nowYearD.format(formatter);      
	      
	      for(ChartDto s:chartDao.chartList(oneYearbefore, nowYear)) {
	    	    data = new HashMap<String, String>();
	    	    data.put("chart_date", s.getChart_date().substring(0,7));
	    	    data.put("chart__total_signup", s.getChart__total_signup());
	    	    data.put("chart_join", s.getChart_join());
	    	    data.put("chart_loan_interest", s.getChart_loan_interest());
	    	    data.put("chart_saving_signup", s.getChart_loan_signup());
	    	    data.put("chart_saving_interest", s.getChart_saving_interest());
	    	    data.put("chart_total_money", s.getChart_total_money());
	    	    data.put("chart_total_person", s.getChart_total_person());
	    	    data.put("chart_withdrawal", s.getChart_withdrawal());
//	    	    data.put("chart_loan_signup", s.getChart_loan_signup());
	    	    list.add(data);
	    	}
	      Map<String, Object> faqList = new HashMap<String, Object>();
	      
	      faqList.put("datas", list);
	      
	      return faqList;
	   }

}
