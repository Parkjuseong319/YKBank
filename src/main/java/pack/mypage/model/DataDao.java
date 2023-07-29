package pack.mypage.model;


import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pack.mypage.controller.QuitBean;
import pack.mypage.controller.UserBean;

@Repository
public class DataDao {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DataMappingInterface mappingInterface;
	
	// 회원 수정 정보 읽어오기
	public UserDto getUserData(int user_no){
		UserDto uDto = mappingInterface.selectUser(user_no);
		
		logger.info("user Dto: "+uDto.getUser_name());
		
		return uDto;
	}
	
	// 비밀번호 변경 전 아이디 & 이름 & 주민등록번호의 일치 여부 확인
	public boolean chkPwdInfo(UserBean bean) {
		boolean b= false;
		UserDto userDto = mappingInterface.chkPwdInfo(bean);
		if(userDto!=null) {
			b=true;
		}
		return b;
	}
	
	// 비밀번호 변경
	public boolean updatePwd(UserBean bean) {
		int re=mappingInterface.updatePwd(bean);
		
		if(re>0) {	// 변경 성공
			return true;
		}else {
			return false;
		}
	}
	
	// 회원 정보 수정
	public boolean updateUserInfo(UserBean bean) {
		int re=mappingInterface.updateUserInfo(bean);
		
		if(re>0) {
			return true;
		}else {
			return false;
		}
	}
	
	// user_no에 해당되는 사용자의 ip 목록 전체 레코드 개수
	public int getUserIpCnt(int user_no) {
		return mappingInterface.getUserIpCnt(user_no);
	}
	
	public int totalCnt(int user_no) {
		return mappingInterface.totalCnt(user_no);
	}
	
	// 회원 ip 불러오기
	public ArrayList<UserIpDto> getUserIpList(int user_no){
		ArrayList<UserIpDto> userArr = (ArrayList<UserIpDto>)mappingInterface.getUserIpList(user_no);
		
		return userArr;
	}
	
	// account 의 잔액 
	public long getUserBalance(int user_no) {
		long balance= mappingInterface.getUserBalance(user_no);
		
		
		//logger.info(Integer.toString(balance));
		return balance;
	}
	
	// 회원 탈퇴 (사용자 데이터 삭제)
	public boolean delUser(int user_no) {
		boolean b=false;
		int re=mappingInterface.delUser(user_no);
		
		if(re>0) b=true;
		
		return b;
	}
	
	public int getPrdtCnt(int user_no) {
		return mappingInterface.getPrdtCnt(user_no);
	}
	
	public int insertQuitInfo(QuitBean bean) {
		return mappingInterface.insertQuitInfo(bean);
	}
	
}
