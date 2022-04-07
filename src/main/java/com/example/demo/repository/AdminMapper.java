package com.example.demo.repository;

import com.example.demo.vo.BoardMaster;
import com.example.demo.vo.MenuTree;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository("AdminMapper")
public interface AdminMapper {

    int insertMenuTree(List<Map<String,Object>> paramMapList);

    List<MenuTree>  findAllMenuTree();

    int deleteMenuTree();

    List<Map<String,Object>> findAllBoardMaster();

    int insertBoardMaster(Map<String, Object> paramMap);

    int existsBoardId(String boardId);
}
