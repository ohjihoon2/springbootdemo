package com.example.demo.service;

import com.example.demo.vo.Visitor;

public interface CommonService {

    void generateSingletonData();

    void refreshSingletonMenuInfo();

    void refreshSingletonCssInfo();

    void refreshSingletonSystemConfigInfo();

    void refreshSingletonPopupInfo();

    int insertVisitor(Visitor visitor);

    void refreshSingletonBannerInfo();
}
