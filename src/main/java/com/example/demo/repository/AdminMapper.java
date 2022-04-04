package com.example.demo.repository;

import com.example.demo.vo.MenuTree;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("AdminMapper")
public interface AdminMapper {

    int insertMenuTree(MenuTree menuTree);
}
