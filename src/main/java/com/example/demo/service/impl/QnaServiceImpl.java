package com.example.demo.service.impl;

import com.example.demo.repository.QnaMapper;
import com.example.demo.service.QnaService;
import com.example.demo.vo.Criteria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QnaServiceImpl implements QnaService {

    private final QnaMapper qnaMapper;


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
}
