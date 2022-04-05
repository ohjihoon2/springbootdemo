package com.example.demo.service;

import com.example.demo.vo.MenuTree;

import java.util.List;
import java.util.Map;

public interface AdminService{

    int addMenuTree(MenuTree menuTree);

    List<Map<String,Object>> findAllMenuTree();
}
