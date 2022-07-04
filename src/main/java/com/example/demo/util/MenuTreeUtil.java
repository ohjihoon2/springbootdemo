package com.example.demo.util;

import com.example.demo.vo.MenuTree;
import com.example.demo.vo.SingletonData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MenuTreeUtil {

    public List<MenuTree> getMenuTree(){
        List<MenuTree> menuList = SingletonData.getInstance().getMenuOneList();
        return menuList;
    }

    public List<MenuTree> getMenuTreeDetail(String lvl){
        List<MenuTree> menuTwoMap = SingletonData.getInstance().getMenuTwoMap(lvl);
        return menuTwoMap;
    }
}
