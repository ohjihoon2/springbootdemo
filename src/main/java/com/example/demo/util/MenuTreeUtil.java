package com.example.demo.util;

import com.example.demo.service.CommonService;
import com.example.demo.vo.MenuTree;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MenuTreeUtil {

    private final CommonService commonService;

    public List<MenuTree> getMenuTree(){
        return commonService.findLinkNameLvl1ByUseYn();
    }

    public List<MenuTree> getMenuTreeDetail(int lvl){
        return commonService.findLinkNameLvl2ByUseYn(lvl);
    }
}
