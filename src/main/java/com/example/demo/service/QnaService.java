package com.example.demo.service;

import com.example.demo.vo.Criteria;

import java.util.List;
import java.util.Map;

public interface QnaService {
    int countByQna(Criteria criteria);

    List<Map<String,Object>> findAllQna(Criteria criteria);

    Map<String, Object> findByIdxQnaConfig(int idx);

    List<Map<String, Object>> findByIdxQna(int idx);
}
