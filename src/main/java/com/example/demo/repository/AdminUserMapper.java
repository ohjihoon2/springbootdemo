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

    Users findByIdxUser(Map<String, Object> paramMap);

    int updateUser(Map<String, Object> paramMap);

    int forceDeleteUser(Map<String, Object> paramMap);

    int resetPassword(Map<String, Object> paramMap);

    List<Map<String, Object>> findAllAdmin(Criteria criteria);

    int countAdmin(Criteria criteria);

    int insertAdmin(Map<String, Object> paramMap);

    int updateAdmin(Map<String, Object> paramMap);

    List<Map<String, Object>> findAllVisitor(Criteria criteria);

    int countVisitor(Criteria criteria);

    List<Map<String, Object>> findByStandardGraphDay();

    List<Map<String, Object>> findByStandardGraphMonth();

    List<Map<String, Object>> findByStandardGraphYear();

    List<Map<String, Object>> findByStandardPieDay();

    List<Map<String, Object>> findByStandardPieMonth();

    List<Map<String, Object>> findByStandardPieYear();
}
