package pack.admin.model;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pack.admin.business.AdminProductBean;

@Mapper
public interface AdminProductDataMapping {
	//금융 상품 생성
	@Insert("insert into finprodt(fin_prdt_code, kor_co_nm, fin_prdt_type, "
			+ "fin_prdt_nm, basic_rate, max_limit, created_user_id, "
			+ "created_dt, prodt_detail, expired) values(#{fin_prdt_code}, #{kor_co_nm}, "
			+ "#{fin_prdt_type}, #{fin_prdt_nm}, #{basic_rate}, #{max_limit}, "
			+ "#{created_user_id}, now(), #{prodt_detail}, #{expired})")
	int insertProdt(AdminProductBean bean);

	//금융 상품 수정 내용 불러오기
	@Select("select * from finprodt where fin_prdt_code=#{fin_prdt_code}")
	AdminProductDto detailProdt(String fin_prdt_code);
	
	//상품 수정
	@Update("update finprodt set fin_prdt_type=#{fin_prdt_type}, fin_prdt_nm=#{fin_prdt_nm}, "
			+ "basic_rate=#{basic_rate}, max_limit=#{max_limit}, prodt_detail=#{prodt_detail}, "
			+ "expired=#{expired} where fin_prdt_code=#{fin_prdt_code}")
	int updateProdt(AdminProductBean bean);
	
	//금융 상품 삭제
	@Delete("delete from finprodt where fin_prdt_code = #{fin_prdt_code}")
	void deleteProdt(String code);
	
	//금융상품 목록 호출
	@Select("select * from finprodt")
	List<AdminProductDto> selectProdtAll();
	
	//상품 생성자 이름
	@Select("select user_name from user where user_no=#{user_no}")
	String callName(int user_no);
	
	//상품명 검색
	@Select("select * from finprodt where fin_prdt_nm like '%${searchValue}%'")
	List<AdminProductDto> adminprdtsearch(String searchValue);
}
