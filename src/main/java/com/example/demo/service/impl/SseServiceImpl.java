package com.example.demo.service.impl;

import com.example.demo.repository.QnaMapper;
import com.example.demo.service.QnaService;
import com.example.demo.service.SseService;
import com.example.demo.util.FileUtil;
import com.example.demo.vo.AttachFile;
import com.example.demo.vo.Criteria;
import com.example.demo.vo.Qna;
import com.example.demo.vo.QnaConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SseServiceImpl implements SseService {

//    private final SseMapper sseMapper;

    @Override
    public SseEmitter subscribe(Long id, String lastEventId) {

        return null;
//        return sseMapper.subscribe();
    }
}
