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

    private List<MenuTree> menuOneList = new ArrayList<>();
    private Map<String, List<MenuTree>> menuTwoMap = new HashMap<>();
    private List<Css> cssList = new ArrayList<>();
    private Map<String, Object> configData 	= new HashMap<>();
    private List<Popup> popupList = new ArrayList<>();
    private List<Banner> bannerList = new ArrayList<>();

//    private List<SiteVO> siteList 	= new ArrayList<SiteVO>();		// 사이트 리스트


    public List<MenuTree>  getMenuTwoMap(String lvl){
        return menuTwoMap.get(lvl);
    }
}
