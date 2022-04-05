package com.example.demo.repository;

import com.example.demo.vo.MenuTree;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository("AdminMapper")
public interface AdminMapper {

    int insertMenuTree(MenuTree menuTree);

    List<Map<String,Object>> findAllMenuTree();
}
