package com.example.demo.service.impl;

import com.example.demo.repository.MypageMapper;
import com.example.demo.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MypageServiceImpl implements MypageService {

    @Autowired
    MypageMapper mypageMapper;

    @Override
    public int withdrawUser(Map<String, Object> paramMap) {
        return mypageMapper.withdrawUser(paramMap);
    }
}
