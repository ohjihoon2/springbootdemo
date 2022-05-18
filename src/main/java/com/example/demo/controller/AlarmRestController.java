package com.example.demo.controller;

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
    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping("/new_notification")
    public SseEmitter getNewNotification() {
        SseEmitter emitter = new SseEmitter();
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
        System.out.println("NotificationRestController.onNotification");
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
