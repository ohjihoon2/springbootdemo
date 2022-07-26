package com.example.demo.service.impl;

import com.example.demo.repository.QnaMapper;
import com.example.demo.service.QnaService;
import com.example.demo.util.FileUtil;
import com.example.demo.vo.AttachFile;
import com.example.demo.vo.Criteria;
import com.example.demo.vo.Qna;
import com.example.demo.vo.QnaConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QnaServiceImpl implements QnaService {

    private final QnaMapper qnaMapper;
    private final FileUtil fileUtil;

    @Override
    public int countByQna(Criteria criteria) {
        return qnaMapper.countByQna(criteria);
    }

    @Override
    public List<Map<String,Object>> findAllQna(Criteria criteria) {
        return qnaMapper.findAllQna(criteria);
    }

    @Override
    public Map<String, Object> findByIdxQnaConfig(int idx) {
        return qnaMapper.findByIdxQnaConfig(idx);
    }

    @Override
    public List<Map<String, Object>> findByIdxQna(int idx) {
        return qnaMapper.findByIdxQna(idx);
    }

    @Override
    public String findAllQaCategory() {
        return qnaMapper.findAllQaCategory();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public int insertQna(MultipartFile[] files, QnaConfig qnaConfig, Qna qna) {

        int result = 0;

        if(files != null){
            // 실제 파일 업로드
            List<AttachFile> fileList = fileUtil.uploadFiles(files, 0,qna.getCreateIdx());

            // DB에 파일 저장
            int idx = fileUtil.saveFile(fileList);

            //attachFileIdx 저장
            qna.setAttachFileIdx(idx);
        }

        qnaMapper.insertQna(qna);
        qnaConfig.setQaIdx(qna.getIdx());
        qnaMapper.insertQnaConfig(qnaConfig);

        return result;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public int updateQuestion(MultipartFile[] files, Qna qna) {

        int result = 0;

        if(files != null){
            // 실제 파일 업로드
            List<AttachFile> fileList = fileUtil.uploadFiles(files, qna.getAttachFileIdx(),qna.getCreateIdx());
            // DB에 파일 저장
            int idx = fileUtil.updateFile(fileList, qna.getAttachFileIdx());
            //attachFileIdx 저장
            qna.setAttachFileIdx(idx);
        }
        result = qnaMapper.updateQuestion(qna);

        return result;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public int additionalInsertQna(MultipartFile[] files, QnaConfig qnaConfig, Qna qna) {

        int result = 0;

        if(files != null){
            // 실제 파일 업로드
            List<AttachFile> fileList = fileUtil.uploadFiles(files, 0,qna.getCreateIdx());

            // DB에 파일 저장
            int idx = fileUtil.saveFile(fileList);

            //attachFileIdx 저장
            qna.setAttachFileIdx(idx);
        }

        qnaMapper.insertQna(qna);
        qnaConfig.setQaIdx(qna.getIdx());
        qnaMapper.updateQnaConfig(qnaConfig);

        return result;
    }

    @Override
    public List<List<AttachFile>> findAttachFileIdxByIdxQna(int idx) {
        List<List<AttachFile>> resultList = new ArrayList<>();

        List<Qna> attachFileIdxByIdxQnaList = qnaMapper.findAttachFileIdxByIdxQna(idx);
        for (Qna qna : attachFileIdxByIdxQnaList) {
            resultList.add(qnaMapper.findByAttachFileIdx(qna.getAttachFileIdx()));
        }

        return resultList;
    }

    @Override
    public List<AttachFile> findAttachFileIdxByMaxIdxQna(int idx) {
        return qnaMapper.findByAttachFileIdx(idx);
    }

    @Override
    public Map<String, Object> findByMaxIdxQna(int idx) {
        return qnaMapper.findByMaxIdxQna(idx);
    }
}
