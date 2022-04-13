package com.example.demo.repository;

import com.example.demo.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository("AdminMapper")
public interface AdminMapper {

    int insertMenuTree(List<Map<String,Object>> paramMapList);

    List<MenuTree>  findAllMenuTree();

    int deleteMenuTree();

    List<Map<String,Object>> findAllBoardMaster(Criteria criteria);

    int insertBoardMaster(Map<String, Object> paramMap);

    int existsBoardId(Map<String,Object> paramMap);

    int updateBoardMaster(Map<String, Object> paramMap);

    BoardMaster findByIdxBoardMaster(int idx);

    int deleteBoardMaster(Map<String, Object> paramMap);

    List<Map<String, Object>> findAllContent(Criteria criteria);

    Content findByIdxContent(int idx);

    int insertContent(Map<String, Object> paramMap);

    int existsContentId(Map<String, Object> paramMap);

    int updateContent(Map<String, Object> paramMap);

    int deleteContent(Map<String, Object> paramMap);

    List<Map<String, Object>> findAllUser(Criteria criteria);

    int countUser(Criteria criteria);

    User findByIdxUser(Map<String, Object> paramMap);

    int updateUser(Map<String, Object> paramMap);

    int forceDeleteUser(Map<String, Object> paramMap);

    int resetPassword(Map<String, Object> paramMap);

    List<Map<String, Object>> findAllAdmin(Criteria criteria);

    int countAdmin(Criteria criteria);

    int existsPassword(Map<String, Object> paramMap);

    int updatePassword(Map<String, Object> paramMap);

    int insertAdmin(Map<String, Object> paramMap);

    int updateAdmin(Map<String, Object> paramMap);
}
