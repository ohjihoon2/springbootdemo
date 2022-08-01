package com.example.demo.service;

import com.example.demo.vo.*;

import java.util.List;
import java.util.Map;

public interface AdminUserService {


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

    List<Map<String, Object>> findByStandardGraph(String standard);

    List<Map<String, Object>> findByStandardPie(String standard);
}
