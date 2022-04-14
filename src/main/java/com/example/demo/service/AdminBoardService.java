package com.example.demo.service;

import com.example.demo.vo.*;

import java.util.List;
import java.util.Map;

public interface AdminBoardService {

    List<Map<String,Object>> findAllBoardMaster(Criteria criteria);

    int insertBoardMaster(Map<String, Object> paramMap);

    int existsBoardId(Map<String,Object> paramMap);

    int updateBoardMaster(Map<String, Object> paramMap);

    BoardMaster findByIdxBoardMaster(int idx);

    int deleteBoardMaster(Map<String, Object> paramMap);

    List<Map<String, Object>> findAllContent(Criteria criteria);

    Content findByIdxContent(int idx);

    int insertContent(Map<String, Object> paramMap);

    int existsContentId(Map<String, Object> paramMap);

    int updateContent(Map<String, Object> paramMap);

    int deleteContent(Map<String, Object> paramMap);

    List<Map<String, Object>> findAllQna(Criteria criteria);

    int countQna(Criteria criteria);
}
