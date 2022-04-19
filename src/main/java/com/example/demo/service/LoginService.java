package com.example.demo.service;

import com.example.demo.vo.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface LoginService extends UserDetailsService {

    int userSave(Users users, HttpServletRequest request);

    Users findByEmailAndUserNm(Map<String, Object> paraMap);

    Users findByUserNmAndUserId(String userNm, String userId);

    void updateUserPwd(Map<String, Object> map);

    Users checkUserByUserId(String userId);

    Map<String, Object> findUserNicknmVerificationYnEmailIdxByUserId(String userId);

    boolean updateVerificationCode(Map<String,Object> paramMap);

    Map<String, Object> verifyMail(String userId, String code);

    int sendVerificationMail(HttpServletRequest request, Map<String, Object> map);

    int forgetPwd(String userNm, String userId);

    int checkUserByUserNicknm(String userNicknm);

    int checkUserByUserEmail(String userEmail);
}
