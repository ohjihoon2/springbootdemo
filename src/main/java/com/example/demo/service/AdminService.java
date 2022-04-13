package com.example.demo.service;

import com.example.demo.vo.*;

import java.util.List;
import java.util.Map;

public interface AdminService{

    int addMenuTree(List<Map<String,Object>> paramMapList);

    List<MenuTree> findAllMenuTree();

    List<Map<String,Object>> findAllBoardMaster(Criteria criteria);

    int insertBoardMaster(Map<String, Object> paramMap);

    int existsBoardId(Map<String,Object> paramMap);

    int updateBoardMaster(Map<String, Object> paramMap);

    BoardMaster findByIdxBoardMaster(int idx);

    int deleteBoardMaster(Map<String, Object> paramMap);

    List<Map<String, Object>> findAllContent(Criteria criteria);

    Content findByIdxContent(int idx);

    int insertContent(Map<String, Object> paramMap);

    int countBoardMaster(Criteria criteria);

    int existsContentId(Map<String, Object> paramMap);

    int updateContent(Map<String, Object> paramMap);

    int deleteContent(Map<String, Object> paramMap);

    int countContent(Criteria criteria);

    List<Map<String, Object>> findAllUser(Criteria criteria);

    int countUser(Criteria criteria);

    User findByIdxUser(Map<String, Object> paramMap);

    int updateUser(Map<String, Object> paramMap);

    int forceDeleteUser(Map<String, Object> paramMap);

    int resetPassword(Map<String, Object> paramMap);

    List<Map<String, Object>> findAllAdmin(Criteria criteria);

    int countAdmin(Criteria criteria);

    int updateAdminSelfInfo(Map<String, Object> paramMap);

    int updatePassword(Map<String, Object> paramMap);

    int insertAdmin(Map<String, Object> paramMap);
}
