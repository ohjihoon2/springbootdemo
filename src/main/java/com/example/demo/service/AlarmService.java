package com.example.demo.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface AlarmService {
    SseEmitter subscribe(Long id, String lastEventId);

    int countReadYn(int userIdx);
}
