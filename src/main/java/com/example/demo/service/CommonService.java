package com.example.demo.service;

import com.example.demo.vo.MenuTree;

import java.util.List;
import java.util.Map;

public interface CommonService {

    int addMenuTree(List<Map<String,Object>> paramMapList);

    List<MenuTree> findAllMenuTree();
}
