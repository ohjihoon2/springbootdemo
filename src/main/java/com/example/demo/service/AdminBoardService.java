package com.example.demo.service;

import com.example.demo.vo.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface AdminBoardService {

    List<Map<String,Object>> findAllBoardMaster(Criteria criteria);

    int insertBoardMaster(Map<String, Object> paramMap);

    int existsBoardId(Map<String,Object> paramMap);

    int updateBoardMaster(Map<String, Object> paramMap);

    Map<String, Object> findByIdxBoardMaster(int idx);

    int deleteBoardMaster(Map<String, Object> paramMap);

    List<Map<String, Object>> findAllContent(Criteria criteria);

    Content findByIdxContent(int idx);

    int insertContent(Map<String, Object> paramMap);

    int existsContentId(Map<String, Object> paramMap);

    int updateContent(Map<String, Object> paramMap);

    int deleteContent(Map<String, Object> paramMap);

    List<Map<String, Object>> findAllQna(Criteria criteria);

    int countQna(Criteria criteria);

    List<Qna> findByIdxQna(int idx);

    int answerQna(Map<String, Object> paramMap, HttpServletRequest request);

    QnaConfig findByIdxQnaConfig(int idx);

    List<Map<String, Object>> findAllFaqMaster(Criteria criteria);

    int insertFaqMaster(Map<String, Object> paramMap);

    int updateFaqMaster(Map<String, Object> paramMap);

    int deleteFaqMaster(Map<String, Object> paramMap);

    List<Map<String, Object>> findNameFaqMaster();

    List<Map<String, Object>> findAllFaq(Criteria criteria);

    int insertFaq(Map<String, Object> paramMap);

    int updateFaq(Map<String, Object> paramMap);

    int deleteFaq(Map<String, Object> paramMap);
}
