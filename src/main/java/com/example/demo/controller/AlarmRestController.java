package com.example.demo.controller;

import com.example.demo.service.AlarmService;
import com.example.demo.vo.Alarm;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequiredArgsConstructor
public class AlarmRestController {
    private final AlarmService alarmService;
    private Long DEFAULT_TIMEOUT = 60L * 1000L * 60L;
    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

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
}
