package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker//WebSocket 서버를 활성화하는 데 사용
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//    STOMP는 Simple Text Oriented Messaging Protocol의 약어, 데이터 교환의 형식과 규칙을 정의하는 메시징 프로토콜
//    STOMP를 사용하는 이유?
//    WebSocket은 통신 프로토콜 일뿐입니다.
//    특정 주제를 구독한 사용자에게만 메시지를 보내는 방법 또는 특정 사용자에게 메시지를 보내는 방법과 같은 내용은 정의하지 않습니다.
//    이러한 기능을 위해서는 STOMP가 필요합니다.


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        클라이언트가 웹 소켓 서버에 연결하는 데 사용할 웹 소켓 엔드 포인트를 등록
//        엔드 포인트 구성에 withSockJS ()를 사용
//        SockJS는 웹 소켓을 지원하지 않는 브라우저에 폴백 옵션을 활성화하는 데 사용
        registry.addEndpoint("/ws").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // "/app" 시작되는 메시지가 message-handling methods 으로 라우팅 되어야 한다는 것을 명시
        registry.setApplicationDestinationPrefixes("/app");
        // "/topic" 시작되는 메시지가 메시지 브로커로 라우팅 되도록 정의
        // 메시지 브로커는 특정 주제를 구독 한 연결된 모든 클라이언트에게 메시지를 broadcast
        registry.enableSimpleBroker("/topic");
    }
}
