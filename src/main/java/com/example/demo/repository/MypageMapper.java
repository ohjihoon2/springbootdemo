package com.example.demo.repository;

import com.example.demo.vo.Users;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository("MypageMapper")
public interface MypageMapper {

    int withdrawUser(Map<String, Object> paramMap);

    Users findUserInfoByIdx(String userId);

    int updateNicknm(Map<String, Object> paramMap);

    int findVerificationCodeByIdxCode(Map<String, Object> paramMap);

    int updateEmail(Map<String, Object> paramMap);

    int updatePhone(Map<String, Object> paramMap);

    int updateProfile(String idx);

    int deleteProfile(int idx);
}
