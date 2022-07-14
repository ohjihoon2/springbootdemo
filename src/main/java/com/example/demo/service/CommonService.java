package com.example.demo.service;

import com.example.demo.vo.MenuTree;

import java.util.List;

public interface CommonService {

    int countAlarmByUserIdx(int userIdx);

    void generateSingletonData();

    void refreshSingletonMenuInfo();

    void refreshSingletonCssInfo();

    void refreshSingletonCodeInfo();

    void refreshSingletonSystemConfigInfo();
}
