package com.example.demo.repository;

import com.example.demo.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository("BoardMapper")
public interface BoardMapper {
    int insertBoard(Board board);

    List<Map<String,Object>> findAllByBoardIdBoard(Criteria criteria);

    Map<String,Object> findAllByIdx(Map<String, Object> paramMap);

    Map<String, Object> findByBoardIdBoardMaster(String boardId);

    int countByBoardIdBoard(Criteria criteria);

    int updateBoard(Board board);

    int deleteAllBoardAdmin(Map<String, Object> paramMap);

    BoardMaster findAllByIdxBoardMaster(Map<String, Object> paramMap);

    int moveBoards(Map<String, Object> paramMap);

    void incrementBoardHit(int idx);

    List<Map<String, Object>> findNoticeByBoardIdBoard(Criteria criteria);

    int findCreateIdxByIdx(Map<String, Object> paramMap);

    int deleteBoardUser(Map<String, Object> paramMap);

    List<Map<String, Object>> findAllByIdxBoardComment(Map<String, Object> paramMap);

    int insertBoardComment(BoardComment comment);

    int updateBoardComment(BoardComment comment);

    int deleteBoardCommentUser(BoardComment comment);

    int deleteBoardCommentAdmin(Map<String, Object> paramMap);

    void deleteBoardCommentWithBoard(Map<String, Object> paramMap);

    List<Map<String, Object>> findBoardMaster();

    int deleteOneBoardAdmin(Map<String, Object> paramMap);

    List<Map<String, Object>> findAllByIdxList(Map<String, Object> paramMap);
}
