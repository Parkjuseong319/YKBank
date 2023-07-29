package pack.paging;

import java.util.ArrayList;

import lombok.Data;
import pack.about.model.AboutDto;
import pack.admin.model.AdminManageDto;
import pack.customer.model.CustomerDto;
import pack.mypage.model.UserIpDto;

@Data
public class PagingProcess {
    private int tot; //전체 레코드 수
    private int plist = 10	; //페이지당 행 수
    private int pagesu; // 전체 페이지 수

    public ArrayList<AdminManageDto> getListdata(ArrayList<AdminManageDto> list, int page) {
        ArrayList<AdminManageDto> result = new ArrayList<AdminManageDto>();

        int start = (page - 1) * plist;    // 0,10,20,...
        int size = plist <= list.size() - start ? plist : list.size() - start; //삼항연산

        for (int i = 0; i < size; i++) {
            result.add(i, list.get(start + i));
        }

        return result;
    }
    
    public ArrayList<CustomerDto> getmailList(ArrayList<CustomerDto> list, int page) {
        ArrayList<CustomerDto> result = new ArrayList<CustomerDto>();

        int start = (page - 1) * plist;    // 0,10,20,...
        int size = plist <= list.size() - start ? plist : list.size() - start; //삼항연산

        for (int i = 0; i < size; i++) {
            result.add(i, list.get(start + i));
        }

        return result;
    }
    
    // 회원 로그
    public ArrayList<UserIpDto> getIpList(ArrayList<UserIpDto> list, int page) {
        ArrayList<UserIpDto> result = new ArrayList<UserIpDto>();

        int start = (page - 1) * plist;    // 0,10,20,...
        int size = plist <= list.size() - start ? plist : list.size() - start; //삼항연산

        for (int i = 0; i < size; i++) {
            result.add(i, list.get(start + i));
        }

        return result;
    }
    
    //총 페이지 수
    public int getPageSu() {
        pagesu = tot / plist;
        if (tot % plist > 0) pagesu += 1;

        return pagesu;
    }
}
