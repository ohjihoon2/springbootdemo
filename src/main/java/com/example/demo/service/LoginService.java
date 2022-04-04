package com.example.demo.service;

import com.example.demo.form.UserSaveForm;
import com.example.demo.vo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface LoginService extends UserDetailsService {

    int userSave(UserSaveForm userSaveForm);

    User findByEmailAndUserNm(Map<String, Object> paraMap);

    User findByUserNmAndUserId(String userNm, String userId);

    void updateUserPwd(Map<String, Object> map);

    User checkUserByUserId(String userId);

    String findUserNicknmByUserId(String userId);

    boolean updateVerificationCode(Map<String,Object> paramMap);

    Map<String, Object> verifyMail(String userId, String code);

    int sendVerificationMail(HttpServletRequest request, Map<String, Object> map);

    int forgetPwd(String userNm, String userId);

    int checkUserByUserNicknm(String userNicknm);

    int checkUserByUserEmail(String userEmail);
}
