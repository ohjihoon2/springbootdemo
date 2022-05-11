package com.example.demo.controller;

import com.example.demo.service.SseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SseController {
    private static final Map<String, SseEmitter> CLIENTS = new ConcurrentHashMap<>();

    private final SseService sseService;

    @GetMapping("/api/subscribe")
    public SseEmitter subscribe(String id) {
        System.out.println("id = " + id);
        SseEmitter emitter = new SseEmitter();
        CLIENTS.put(id, emitter);

        emitter.onTimeout(() -> CLIENTS.remove(id));
        emitter.onCompletion(() -> CLIENTS.remove(id));
        return emitter;
    }

    @GetMapping("/api/publish")
    public void publish(String message) {
        System.out.println("message = " + message);
        Set<String> deadIds = new HashSet<>();

        CLIENTS.forEach((id, emitter) -> {
            try {
                emitter.send(message, MediaType.APPLICATION_JSON);
            } catch (Exception e) {
                deadIds.add(id);
                log.warn("disconnected id : {}", id);
            }
        });

        deadIds.forEach(CLIENTS::remove);
    }

    @GetMapping(value = "/subscribe/{id}", produces = "text/event-stream")
    public SseEmitter subscribe(@PathVariable Long id,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return sseService.subscribe(id, lastEventId);
    }
}
