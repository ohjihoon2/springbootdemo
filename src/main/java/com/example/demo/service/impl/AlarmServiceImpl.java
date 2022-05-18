package com.example.demo.service.impl;

import com.example.demo.repository.AlarmMapper;
import com.example.demo.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {
    private final AlarmMapper alarmMapper;

    @Override
    public SseEmitter subscribe(Long userId, String lastEventId) {
       /*String id = userId + "_" + System.currentTimeMillis();

        // 2
        SseEmitter emitter = emitterRepository.save(id, new SseEmitter(DEFAULT_TIMEOUT));

        emitter.onCompletion(() -> emitterRepository.deleteById(id));
        emitter.onTimeout(() -> emitterRepository.deleteById(id));

        // 3
        // 503 에러를 방지하기 위한 더미 이벤트 전송
        sendToClient(emitter, id, "EventStream Created. [userId=" + userId + "]");

        // 4
        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (!lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithId(String.valueOf(userId));
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
        }

        return emitter;*/
        return null;
//        return alertMapper.subscribe();
    }
/*

    private void sendToClient(SseEmitter emitter, String id, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(id)
                    .name("sse")
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(id);
            throw new RuntimeException("연결 오류!");
        }
    }
*/

    @Override
    public int countReadYn(int userIdx) {
        return alarmMapper.countReadYn(userIdx);
    }
}
