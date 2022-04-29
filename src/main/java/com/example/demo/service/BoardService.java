package com.example.demo.service;

import com.example.demo.vo.Board;
import com.example.demo.vo.BoardComment;
import com.example.demo.vo.BoardMaster;
import com.example.demo.vo.Criteria;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface BoardService {

    int insertBoard(MultipartFile[] files,MultipartFile thumb, Board board);

    List<Map<String,Object>> findAllByBoardIdBoard(Criteria criteria);

    Map<String,Object> findAllByIdx(Map<String, Object> paramMap);

    Map<String, Object> findByBoardIdBoardMaster(String boardId);

    int countByBoardIdBoard(Criteria criteria);

    int updateBoard(MultipartFile[] files, MultipartFile thumb,Board board);

    int deleteBoardUser(Map<String, Object> paramMap);

    int deleteAllBoardAdmin(Map<String, Object> paramMap);

    BoardMaster findAllByIdxBoardMaster(Map<String, Object> paramMap);

    int moveBoards(Map<String, Object> paramMap);

    void addHit(HttpServletRequest request, HttpServletResponse response, int idx);

    List<Map<String, Object>> findNoticeByBoardIdBoard(Criteria criteria);

    List<Map<String, Object>> findAllByIdxBoardComment(Map<String, Object> paramMap);

    int insertBoardComment(BoardComment comment);

    int updateBoardComment(BoardComment comment);

    int deleteBoardCommentUser(BoardComment comment);

    int deleteBoardCommentAdmin(BoardComment comment);

    List<Map<String, Object>> findBoardMaster();

    int deleteOneBoardAdmin(Map<String, Object> paramMap);
}
