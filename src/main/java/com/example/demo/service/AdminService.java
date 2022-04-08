package com.example.demo.service;

import com.example.demo.vo.BoardMaster;
import com.example.demo.vo.Content;
import com.example.demo.vo.MenuTree;
import com.example.demo.vo.Search;

import java.util.List;
import java.util.Map;

public interface AdminService{

    int addMenuTree(List<Map<String,Object>> paramMapList);

    List<MenuTree> findAllMenuTree();

    List<Map<String,Object>> findAllBoardMaster();

    int insertBoardMaster(Map<String, Object> paramMap);

    int existsBoardId(Map<String,Object> paramMap);

    int updateBoardMaster(Map<String, Object> paramMap);

    BoardMaster findByIdxBoardMaster(int idx);

    int deleteBoardMaster(Map<String, Object> paramMap);

    List<Map<String, Object>> findAllContent(Search search);

    Content findByIdxContent(int idx);

    int insertContent(Map<String, Object> paramMap);
}
