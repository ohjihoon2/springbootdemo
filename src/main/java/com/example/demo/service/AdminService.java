package com.example.demo.service;

import com.example.demo.vo.MenuTree;

import java.util.List;
import java.util.Map;

public interface AdminService{

    int addMenuTree(List<Map<String,Object>> paramMapList) throws Exception;

    List<MenuTree> findAllMenuTree();
}
