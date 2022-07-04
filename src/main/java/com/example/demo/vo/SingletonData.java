package com.example.demo.vo;

import lombok.Data;

import java.util.*;

@Data
public class SingletonData {
    private static SingletonData singletonData;

    private SingletonData() {

    }

    public static SingletonData getInstance() {
        if(singletonData == null) {
            singletonData = new SingletonData();
        }
        return singletonData;
    }

//    private List<SiteVO> siteList 	= new ArrayList<SiteVO>();		// 사이트 리스트
//    private List<PopupVO> 		popupList 	= new ArrayList<PopupVO>();		// 팝업 리스트
//    private Map<String, List<CustomBannerVO>> 	bannerList;

    private List<MenuTree> menuOneList = new ArrayList<>();
    private Map<String, List<MenuTree>> menuTwoMap = new HashMap<>();

    public List<MenuTree>  getMenuTwoMap(String lvl){
        return menuTwoMap.get(lvl);
    }
}
