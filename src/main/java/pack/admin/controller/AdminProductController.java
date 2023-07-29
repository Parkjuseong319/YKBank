package pack.admin.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.admin.business.AdminProductBean;
import pack.admin.model.AdminProductDao;
import pack.admin.model.AdminProductDto;

@Controller
public class AdminProductController {
	@Autowired
	private AdminProductDao productDao;

	// 상품 리스트
	@GetMapping("admin_prodt_list")
	public String listProcess(Model model, HttpSession session) {
		ArrayList<AdminProductDto> list = (ArrayList<AdminProductDto>) productDao.allProductList();
		String name = productDao.callName((int) session.getAttribute("user_no"));
		model.addAttribute("user_name", name);
		model.addAttribute("datas", list);
		return "html.product/admin_prodt_list";
	}

	// 상품 추가 입력창
	@GetMapping("admin_prodt_insert")
	public String insertForm(Model model, HttpSession session) {
		model.addAttribute("name", session.getAttribute("user_no"));
		return "html.product/admin_prodt_insert";
	}

	// 상품 추가 프로세스
	@GetMapping("insert")
	public String insertProcess(AdminProductBean bean) {
		boolean b = productDao.insertProdt(bean);
		if (b) {
			return "redirect:/admin_prodt_list";
		} else {
			return "html.error/error";
		}
	}

	// 상품 삭제
	@GetMapping("admin_prodt_delete")
	public String deleteProcess(@RequestParam("fin_prdt_code") String fin_prdt_code) {
		productDao.deleteProdt(fin_prdt_code);
		return "redirect:/admin_prodt_list";
	}

	// 상품 상세 정보(관리자), 금융 상품 코드 가져옴
	@GetMapping("admin_prodt_detail")
	public String prdtDetail(Model model, @RequestParam("fin_prdt_code") String fin_prdt_code) {
		AdminProductDto dto = productDao.detailProdt(fin_prdt_code);
		model.addAttribute("data", dto);
		return "html.product/admin_prodt_detail";
	}

	// 상품 수정
	@GetMapping("updateprodt")
	public String updateProcess(AdminProductBean bean, Model model) {
		boolean b = productDao.updateProdt(bean); // 상품 수정 후 리스트 호출
		ArrayList<AdminProductDto> list = (ArrayList<AdminProductDto>) productDao.allProductList();
		model.addAttribute("datas", list);
		if (b) {
			return "html.product/admin_prodt_list";
		} else {
			return "html.error/error";
		}
	}

	// 상품 검색
	@GetMapping("admin_prodt_search")
	public String adminprdtsearch(Model model, @RequestParam("searchValue") String searchValue) {
		ArrayList<AdminProductDto> list = (ArrayList<AdminProductDto>) productDao.adminprdtsearch(searchValue);

		model.addAttribute("datas", list);

		return "html.product/admin_prodt_list";
	}
}
