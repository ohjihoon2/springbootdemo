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

    int countBoardMaster(Criteria criteria);

    int existsContentId(Map<String, Object> paramMap);

    int updateContent(Map<String, Object> paramMap);

    int deleteContent(Map<String, Object> paramMap);

    int countContent(Criteria criteria);

    List<Map<String, Object>> findAllUser(Criteria criteria);

    int countUser(Criteria criteria);

    User findByIdxUser(int idx);

    int updateUser(Map<String, Object> paramMap);

    int withdrawUser(Map<String, Object> paramMap);

    int deleteUser();
}
