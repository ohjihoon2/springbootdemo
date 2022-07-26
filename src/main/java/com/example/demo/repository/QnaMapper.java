package com.example.demo.repository;

import com.example.demo.vo.AttachFile;
import com.example.demo.vo.Criteria;
import com.example.demo.vo.Qna;
import com.example.demo.vo.QnaConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository("QnaMapper")
public interface QnaMapper {

    int countByQna(Criteria criteria);

    List<Map<String,Object>> findAllQna(Criteria criteria);

    Map<String, Object> findByIdxQnaConfig(int idx);

    List<Map<String, Object>> findByIdxQna(int idx);

    String findAllQaCategory();

    void insertQnaConfig(QnaConfig qnaConfig);

    int insertQna(Qna qna);

    int updateQuestion(Qna qna);

    void updateQnaConfig(QnaConfig qnaConfig);

    List<Qna> findAttachFileIdxByIdxQna(int idx);

    List<AttachFile> findByAttachFileIdx(Integer attachFileIdx);

    Map<String, Object> findByMaxIdxQna(int idx);
}
