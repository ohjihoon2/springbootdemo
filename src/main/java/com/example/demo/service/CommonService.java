package com.example.demo.service;

import com.example.demo.vo.Visitor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface CommonService {

    void generateSingletonData();

    void refreshSingletonMenuInfo();

    void refreshSingletonCssInfo();

    void refreshSingletonSystemConfigInfo();

    void refreshSingletonPopupInfo();

    int insertVisitor(Visitor visitor);

    void refreshSingletonBannerInfo();

    int updatePassword(Map<String, Object> paramMap);

    int checkUserByUserNicknm(String userNicknm);

    int checkUserByUserEmail(String userEmail);

    int sendVerificationMail(HttpServletRequest request, Map<String, Object> mailMap);
}
