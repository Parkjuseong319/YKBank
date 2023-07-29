package pack.admin.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pack.admin.business.AdminProductBean;

@Repository
public class AdminProductDao {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AdminProductDataMapping mapping;
	
	//상품 추가(관리자)
	public boolean insertProdt(AdminProductBean bean) {
		try {
			int re = mapping.insertProdt(bean);
			if (re > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.info("insertProdt 실패 : " + e.getMessage());
			return false;
		}
	}
	
	//상품 수정 내용 불러오기
	public AdminProductDto detailProdt(String fin_prdt_code) {
		AdminProductDto dto = mapping.detailProdt(fin_prdt_code);
		return dto;
	}
	
	//상품 수정
	public boolean updateProdt(AdminProductBean bean) {
		int b = mapping.updateProdt(bean);
		if(b > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	//상품 리스트
	public List<AdminProductDto> allProductList(){
		List<AdminProductDto> list = mapping.selectProdtAll();
		return list;
	}
	
	//상품 생성자 이름
	public String callName(int user_no) {
		String name = mapping.callName(user_no);
		return name;
	}
	
	//상품 삭제
	public void deleteProdt(String code) {
		mapping.deleteProdt(code);
	}

	//상품 검색
	public List<AdminProductDto> adminprdtsearch(String searchValue){
		List<AdminProductDto> list = mapping.adminprdtsearch(searchValue);
		return list;
	}
}
