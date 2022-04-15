package com.example.demo.repository;

import com.example.demo.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository("AdminUserMapper")
public interface AdminUserMapper {

    List<Map<String, Object>> findAllUser(Criteria criteria);

    int countUser(Criteria criteria);

    User findByIdxUser(Map<String, Object> paramMap);

    int updateUser(Map<String, Object> paramMap);

    int forceDeleteUser(Map<String, Object> paramMap);

    int resetPassword(Map<String, Object> paramMap);

    List<Map<String, Object>> findAllAdmin(Criteria criteria);

    int countAdmin(Criteria criteria);

    int updatePassword(Map<String, Object> paramMap);

    int insertAdmin(Map<String, Object> paramMap);

    int updateAdmin(Map<String, Object> paramMap);

    String getResetPassword();

    String findPasswordByIdx(Map<String, Object> paramMap);
}
