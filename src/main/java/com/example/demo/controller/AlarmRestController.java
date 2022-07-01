package com.example.demo.controller;

import com.example.demo.service.AlarmService;
import com.example.demo.vo.Alarm;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequiredArgsConstructor
public class AlarmRestController {
    private final AlarmService alarmService;
    private Long DEFAULT_TIMEOUT = 60L * 1000L * 60L;
    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public static Map<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    @GetMapping( value = "/new_alarm",  produces = "text/event-stream")
    public SseEmitter getNewNotification() {
        System.out.println("AlarmRestController.getNewNotification");
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        this.emitters.add(emitter);

        emitter.onCompletion(() -> this.emitters.remove(emitter));
        emitter.onTimeout(() -> {
            emitter.complete();
            this.emitters.remove(emitter);
        });

        return emitter;
    }

    @EventListener
    public void onNotification(Alarm alarm) {
        System.out.println("AlarmRestController.onNotification");
        List<SseEmitter> deadEmitters = new ArrayList<>();
        this.emitters.forEach(emitter -> {
            try {
                emitter.send(alarm);
            } catch (Exception e) {
                deadEmitters.add(emitter);
            }
        });
        this.emitters.remove(deadEmitters);
    }


    @CrossOrigin
    @GetMapping(value = "/sub", consumes = MediaType.ALL_VALUE)
    public SseEmitter subscribe(HttpServletRequest request) {

        // 토큰에서 user의 pk값 파싱
        HttpSession session = request.getSession();
        Long userIdx = Long.parseLong(String.valueOf(session.getAttribute("idx")));

        // 현재 클라이언트를 위한 SseEmitter 생성
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            // 연결!!
            sseEmitter.send(SseEmitter.event().name("connect"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // user의 pk값을 key값으로 해서 SseEmitter를 저장
        sseEmitters.put(userIdx, sseEmitter);

        sseEmitter.onCompletion(() -> sseEmitters.remove(userIdx));
        sseEmitter.onTimeout(() -> sseEmitters.remove(userIdx));
        sseEmitter.onError((e) -> sseEmitters.remove(userIdx));

        return sseEmitter;
    }
}
