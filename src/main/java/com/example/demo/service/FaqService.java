package com.example.demo.service;

import com.example.demo.vo.Criteria;

import java.util.Map;

public interface FaqService {
    Map<String, Object> findFaqNmFaqMaster();

    Map<String, Object> findAllFaq(Criteria criteria);

    Map<String, Object> findAllByIdx(Criteria criteria);

    int countByFaq(Criteria criteria);

    int countByIdxFaq(Criteria criteria);
}
