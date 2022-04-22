package com.example.demo.repository;

import com.example.demo.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository("AdminBoardMapper")
public interface AdminBoardMapper {


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

    int answerQna(Qna qna);

    int updateQnaConfig(Map<String, Object> paramMap);

    String findUserEmailByIdx(int createIdx);

    QnaConfig findByIdxQnaConfig(int idx);

    List<Map<String, Object>> findAllFaqMaster(Criteria criteria);

    void deleteBoard(Map<String, Object> paramMap);

    int insertFaqMaster(Map<String, Object> paramMap);

    int updateFaqMaster(Map<String, Object> paramMap);

    int deleteFaqMaster(Map<String, Object> paramMap);

    List<Map<String, Object>> findNameFaqMaster();

    List<Map<String, Object>> findAllFaq(Criteria criteria);

    int insertFaq(Map<String, Object> paramMap);

    int updateFaq(Map<String, Object> paramMap);

    int deleteFaq(Map<String, Object> paramMap);

    void deleteFaqGroup(Map<String, Object> paramMap);

    int deleteQna(Map<String, Object> paramMap);

    int deleteQnaConfig(Map<String, Object> paramMap);

    FaqMaster findByIdxFaqMaster(int idx);

    Faq findByIdxFaq(int idx);
}
