package com.example.demo.service;

import com.example.demo.vo.*;

import java.util.List;
import java.util.Map;

public interface AdminBasicService {

    int addMenuTree(List<Map<String,Object>> paramMapList);

    List<MenuTree> findAllMenuTree();

    List<Map<String,Object>> findAllCssContent(Criteria criteria);

    Css findByIdxCssContent(int idx);

    int insertCssContent(Map<String, Object> paramMap);

    int updateCssContent(Map<String, Object> paramMap);

    int deleteCssContent(Map<String, Object> paramMap);
}
