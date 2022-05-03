package com.example.demo.service.impl;

import com.example.demo.repository.FaqMapper;
import com.example.demo.service.FaqService;
import com.example.demo.vo.Criteria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService {

    private final FaqMapper faqMapper;


    @Override
    public Map<String, Object> findFaqNmFaqMaster() {
        return faqMapper.findFaqNmFaqMaster();
    }

    @Override
    public List<Map<String,Object>> findAllFaq(Criteria criteria) {
        return faqMapper.findAllFaq(criteria);
    }

    @Override
    public Map<String, Object> findAllByIdx(Criteria criteria) {
        return faqMapper.findAllByIdx(criteria);
    }

    @Override
    public int countByFaq(Criteria criteria) {
        return faqMapper.countByFaq(criteria);
    }

    @Override
    public int countByIdxFaq(Criteria criteria) {
        return faqMapper.countByIdxFaq(criteria);
    }
}
