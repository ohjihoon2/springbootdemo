package com.example.demo.service;

import com.example.demo.vo.Users;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface MypageService {
    int withdrawUser(Map<String, Object> paramMap);

    Users findUserInfoByIdx(String idx);

    int updateProfile(MultipartFile file, String idx);

    int updateNicknm(Map<String, Object> paramMap);

    int updateEmail(Map<String, Object> paramMap);

    int sendVerificationMail(HttpServletRequest request, Map<String, Object> map);

    int updatePhone(Map<String, Object> paramMap);

    int deleteProfile(int idx);
}
