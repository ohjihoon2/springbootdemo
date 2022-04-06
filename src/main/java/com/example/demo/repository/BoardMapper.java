package com.example.demo.repository;

import com.example.demo.vo.AttachFile;
import com.example.demo.vo.Board;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository("BoardMapper")
public interface BoardMapper {
    int insertBoard(Board board);

    List<Board> findByMasterIdxSearch(Map<String, Object> paramMap);
}
