package com.example.demo.service;

import com.example.demo.vo.BoardMaster;
import com.example.demo.vo.MenuTree;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface AdminService{

    int addMenuTree(List<Map<String,Object>> paramMapList);

    List<MenuTree> findAllMenuTree();

    List<BoardMaster> findAllBoradMaster();

    int addBoardMaster(Map<String, Object> paramMap);
}
