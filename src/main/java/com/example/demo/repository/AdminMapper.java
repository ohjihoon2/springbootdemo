package com.example.demo.repository;

import com.example.demo.vo.BoardMaster;
import com.example.demo.vo.Content;
import com.example.demo.vo.MenuTree;
import com.example.demo.vo.Search;
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

    List<Map<String,Object>> findAllBoardMaster(Search search);

    int insertBoardMaster(Map<String, Object> paramMap);

    int existsBoardId(Map<String,Object> paramMap);

    int updateBoardMaster(Map<String, Object> paramMap);

    BoardMaster findByIdxBoardMaster(int idx);

    int deleteBoardMaster(Map<String, Object> paramMap);

    List<Map<String, Object>> findAllContent(Search search);

    Content findByIdxContent(int idx);

    int insertContent(Map<String, Object> paramMap);
}
