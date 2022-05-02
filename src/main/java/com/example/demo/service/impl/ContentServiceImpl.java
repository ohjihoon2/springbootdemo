package com.example.demo.service.impl;

import com.example.demo.repository.ContentMapper;
import com.example.demo.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

    private final ContentMapper contentMapper;


    @Override
    public Map<String, Object> findAllByContentId(Map<String, Object> paramMap) {
        return contentMapper.findAllByContentId(paramMap);
    }
}
