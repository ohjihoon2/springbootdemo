package com.example.demo.service;

import com.example.demo.vo.BoardMaster;
import com.example.demo.vo.MenuTree;

import java.util.List;
import java.util.Map;

public interface AdminService{

    int addMenuTree(List<Map<String,Object>> paramMapList);

    List<MenuTree> findAllMenuTree();

    List<BoardMaster> findAllBoradMaster();
}
