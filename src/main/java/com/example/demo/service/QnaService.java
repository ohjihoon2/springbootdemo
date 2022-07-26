package com.example.demo.service;

import com.example.demo.vo.AttachFile;
import com.example.demo.vo.Criteria;
import com.example.demo.vo.Qna;
import com.example.demo.vo.QnaConfig;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface QnaService {
    int countByQna(Criteria criteria);

    List<Map<String,Object>> findAllQna(Criteria criteria);

    Map<String, Object> findByIdxQnaConfig(int idx);

    List<Map<String, Object>> findByIdxQna(int idx);

    String findAllQaCategory();

    int insertQna(MultipartFile[] files, QnaConfig qnaConfig, Qna qna);

    int updateQuestion(MultipartFile[] files, Qna qna);

    int additionalInsertQna(MultipartFile[] files, QnaConfig qnaConfig, Qna qna);

    List<List<AttachFile>> findAttachFileIdxByIdxQna(int idx);

    List<AttachFile> findAttachFileIdxByMaxIdxQna(int qaIdx);

    Map<String, Object> findByMaxIdxQna(int qaIdx);
}
