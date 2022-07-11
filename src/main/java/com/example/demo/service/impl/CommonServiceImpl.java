package com.example.demo.service.impl;

import com.example.demo.repository.CommonMapper;
import com.example.demo.service.CommonService;
import com.example.demo.vo.MenuTree;
import com.example.demo.vo.SingletonData;
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
    public int countAlarmByUserIdx(int userIdx) {
        return commonMapper.countAlarmByUserIdx(userIdx);
    }

    @Override
    public void generateSingletonData() {
        //동적메뉴 설정
        this.refreshSingletonMenuInfo();
        //css
        this.refreshSingletonCssInfo();

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

    }
}
