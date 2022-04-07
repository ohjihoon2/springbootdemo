package com.example.demo.service;

import com.example.demo.vo.BoardMaster;
import com.example.demo.vo.MenuTree;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface AdminService{

    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    int addMenuTree(List<Map<String,Object>> paramMapList);

    List<MenuTree> findAllMenuTree();

    List<Map<String,Object>> findAllBoardMaster();

    int addBoardMaster(Map<String, Object> paramMap);

    int existsBoardId(String boardId);
}
