package com.example.demo.repository;

import com.example.demo.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository("AdminBasicMapper")
public interface AdminBasicMapper {

    int insertMenuTree(List<Map<String,Object>> paramMapList);

    List<MenuTree>  findAllMenuTree();

    int deleteMenuTree();

    List<Css> findAllCssContent(Criteria criteria);

    Css findByIdxCssContent(int idx);

    int insertCssContent(Map<String, Object> paramMap);

    int updateCssContent(Map<String, Object> paramMap);

    int deleteCssContent(Map<String, Object> paramMap);
}
