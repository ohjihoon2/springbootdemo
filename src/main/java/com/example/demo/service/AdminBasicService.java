package com.example.demo.service;

import com.example.demo.vo.*;

import java.util.List;
import java.util.Map;

public interface AdminBasicService {

    int addMenuTree(List<Map<String,Object>> paramMapList);

    List<MenuTree> findAllMenuTree();

}
