package com.example.demo.service.impl;

import com.example.demo.repository.CommonMapper;
import com.example.demo.service.CommonService;
import com.example.demo.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

    private final CommonMapper commonMapper;

    @Override
    public void generateSingletonData() {
        // 동적메뉴
        this.refreshSingletonMenuInfo();
        // css
        this.refreshSingletonCssInfo();
        // 사이트 설정
        this.refreshSingletonSystemConfigInfo();
        // 팝업 리스트
        this.refreshSingletonPopupInfo();

        this.refreshSingletonBannerInfo();

    }

    @Override
    public void refreshSingletonMenuInfo() {
        SingletonData singleton = SingletonData.getInstance();

        // lvl1
        List<MenuTree> menuTreeLvlOne = commonMapper.findLinkNameLvl1ByUseYn();
        Map<String, List<MenuTree>> menuTreeLvlTwo = new HashMap<>();

        // lvl2
        for (int i = 0; i < menuTreeLvlOne.size(); i++) {
            String idx = String.valueOf(menuTreeLvlOne.get(i).getIdx());
            List<MenuTree> menutreeTwoList = commonMapper.findLinkNameLvl2ByUseYn(idx);
            menuTreeLvlTwo.put(idx,menutreeTwoList);
        }

        singleton.setMenuOneList(menuTreeLvlOne);
        singleton.setMenuTwoMap(menuTreeLvlTwo);
    }

    @Override
    public void refreshSingletonCssInfo() {
        SingletonData singleton = SingletonData.getInstance();
        List<Css> cssList = commonMapper.findCssContent();
        singleton.setCssList(cssList);

    }

    @Override
    public void refreshSingletonSystemConfigInfo() {
        SingletonData singleton = SingletonData.getInstance();
        singleton.setConfigData(commonMapper.findSystemConfig());
    }

    @Override
    public int insertVisitor(Visitor visitor) {
        return commonMapper.insertVisitor(visitor);
    }

    @Override
    public void refreshSingletonBannerInfo() {
        SingletonData singleton = SingletonData.getInstance();
        singleton.setBannerList(commonMapper.findBanner());
    }

    @Override
    public void refreshSingletonPopupInfo() {
        SingletonData singleton = SingletonData.getInstance();
        singleton.setPopupList(commonMapper.findPopup());
    }
}
