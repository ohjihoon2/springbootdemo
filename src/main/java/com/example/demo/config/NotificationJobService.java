package com.example.demo.config;

import com.example.demo.vo.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationJobService {

    private final ApplicationEventPublisher eventPublisher;

    private static final int REPEAT = 3;
    private final Map<ResponseBodyEmitter, AtomicInteger> emitterCountMap = new HashMap<>();

    public void add(ResponseBodyEmitter emitter) {
        System.out.println("NotificationJobService.add");
        emitterCountMap.put(emitter, new AtomicInteger(0));
    }
/*
    @Scheduled(fixedRate = 1000L)
    public void emit() {
        System.out.println("NotificationJobService.emit");
        List<ResponseBodyEmitter> toBeRemoved = new ArrayList<>(emitterCountMap.size());

        for (Map.Entry<ResponseBodyEmitter, AtomicInteger> entry : emitterCountMap.entrySet()) {

            Integer count = entry.getValue().incrementAndGet();


            User user = new RestTemplate().getForObject("https://jsonplaceholder.typicode.com/users/{id}", User.class, count);

            ResponseBodyEmitter emitter = entry.getKey();
            try {
                emitter.send(user);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                toBeRemoved.add(emitter);
            }

            if (count >= REPEAT) {
                toBeRemoved.add(emitter);
            }
        }

        for (ResponseBodyEmitter emitter : toBeRemoved) {
            emitterCountMap.remove(emitter);
        }
    }*/


//    @Scheduled(fixedRate = 4000)
    public void publishJobNotifications() throws InterruptedException {
        System.out.println("NotificationJobService.publishJobNotifications");

        // 시간당 데이터 넣는건디


        Integer jobId = Notification.getNextJobId();
        Notification nStarted = new Notification("Job No. " + jobId + " started.", new Date());

        this.eventPublisher.publishEvent(nStarted);

        Thread.sleep(2000);
        Notification nFinished = new Notification("Job No. " + jobId + " finished.", new Date());
        this.eventPublisher.publishEvent(nFinished);
    }

}
