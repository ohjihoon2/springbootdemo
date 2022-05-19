package com.example.demo.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface AlarmService {
    SseEmitter subscribe(int id, String lastEventId);

    int countReadYn(int userIdx);
}
