package com.example.demo.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseService {
    SseEmitter subscribe(Long id, String lastEventId);
}
