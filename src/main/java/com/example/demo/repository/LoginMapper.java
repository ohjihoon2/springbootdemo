package com.example.demo.repository;

import com.example.demo.vo.Users;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository("LoginMapper")
public interface LoginMapper {
    Users findByUserId(String userId);

    int countByUserId(String userId);

    int saveUser(Users users);

    int saveAdminTest(Map<String,Object> paramMap);

    int saveUserTest(Map<String,Object> paramMap);

    Users findByEmailAndUserNm(String email, String userNm);

    Users findByUserNmAndUserId(String userNm, String userId);

    void updateUserPwd(Map<String, Object> map);

    Map<String, Object> findUserNicknmVerificationYnEmailIdxByUserId(String userId);

    boolean updateVerificationCode(Map<String,Object> paramMap);

    int findVerificationCodeByUserIdCode(String userId, String code);

    int updateverificationYn(String userId, String code);

    int existByUserNicknm(String userNicknm);

    int existByUserEmail(String userEmail);

    void updateLastLoginDate(String userId);
}
