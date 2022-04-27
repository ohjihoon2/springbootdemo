package com.example.demo.service;

import com.example.demo.vo.Board;
import com.example.demo.vo.BoardMaster;
import com.example.demo.vo.Criteria;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BoardService {

    int insertBoard(MultipartFile[] files, Board board);

    List<Map<String,Object>> findAllByBoardIdBoard(Criteria criteria);

    Board findAllByIdx(Map<String, Object> paramMap);

    Map<String, Object> findByBoardIdBoardMaster(String boardId);

    int countByBoardIdBoard(Criteria criteria);

    int updateBoard(MultipartFile[] files, Board board);

    int deleteBoard(Map<String, Object> paramMap);

    BoardMaster findAllByIdxBoardMaster(Map<String, Object> paramMap);

    int moveBoard(Map<String, Object> paramMap);
}
