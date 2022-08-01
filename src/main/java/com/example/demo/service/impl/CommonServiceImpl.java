package com.example.demo.service.impl;

import com.example.demo.repository.CommonMapper;
import com.example.demo.service.CommonService;
import com.example.demo.service.EmailService;
import com.example.demo.util.RandomString;
import com.example.demo.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

    private final CommonMapper commonMapper;
    private final EmailService emailService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



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

    @Override
    public int updatePassword(Map<String, Object> paramMap) {
        int result = 0 ;

        String password = String.valueOf(paramMap.get("password"));
        String newPassword = String.valueOf(paramMap.get("newPassword"));
        String encodePassword = commonMapper.findPasswordByIdx(paramMap);

        paramMap.put("newPwd",passwordEncoder.encode(newPassword));

        if(passwordEncoder.matches(password, encodePassword)) {
            if (commonMapper.updatePassword(paramMap) == 1) {
                result = 1;
            }
        }

        return result;
    }

    @Override
    public int checkUserByUserNicknm(String userNicknm) {
        return commonMapper.existByUserNicknm(userNicknm);
    }

    @Override
    public int checkUserByUserEmail(String userEmail) {
        return commonMapper.existByUserEmail(userEmail);
    }

    @Override
    public int sendVerificationMail(HttpServletRequest request, Map<String, Object> mailMap) {
        int result = 0;
        String ranPw = mailMap.get("ranPw").toString();
        String userId = mailMap.get("userId").toString();
        String to = mailMap.get("userEmail").toString();
        String subject = mailMap.get("subject").toString();
        String text = mailMap.get("text").toString();

        boolean res = emailService.sendMail(to, subject, text);

        Map<String,Object> paraMap = new HashMap<>();

        paraMap.put("VerificationCode",ranPw);
        paraMap.put("userId",userId);

        if (res) {
            if(commonMapper.updateVerificationCode(paraMap)){
                result = 1;
            }else {
                result = 0;
            }
        } else {
            result = 0;
        }

        return result;
    }

}
