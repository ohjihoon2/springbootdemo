package com.example.demo.service;

import com.example.demo.vo.MenuTree;
import com.example.demo.vo.Visitor;

import java.util.List;

public interface CommonService {

    int countAlarmByUserIdx(int userIdx);

    void generateSingletonData();

    void refreshSingletonMenuInfo();

    void refreshSingletonCssInfo();

    void refreshSingletonSystemConfigInfo();

    int insertVisitor(Visitor visitor);
}
