package com.example.demo.repository;

import com.example.demo.form.UserSaveForm;
import com.example.demo.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository("LoginMapper")
public interface LoginMapper {
    User findByUserId(String userId);

    int countByUserId(String userId);

    int saveUser(UserSaveForm userSaveForm);

    int saveAdminTest(Map<String,Object> paramMap);

    int saveUserTest(Map<String,Object> paramMap);

    User findByEmailAndUserNm(String email, String userNm);

    User findByUserNmAndUserId(String userNm, String userId);

    void updateUserPwd(Map<String, Object> map);

    Map<String, Object> findUserNicknmVerificationYnEmailByUserId(String userId);

    boolean updateVerificationCode(Map<String,Object> paramMap);

    int findVerificationCodeByUserIdCode(String userId, String code);

    int updateverificationYn(String userId, String code);

    int existByUserNicknm(String userNicknm);

    int existByUserEmail(String userEmail);
}
