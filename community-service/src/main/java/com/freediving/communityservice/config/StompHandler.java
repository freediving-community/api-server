// package com.freediving.communityservice.config;
//
// import org.springframework.context.event.EventListener;
// import org.springframework.messaging.Message;
// import org.springframework.messaging.MessageChannel;
// import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
// import org.springframework.messaging.support.ChannelInterceptor;
// import org.springframework.stereotype.Component;
// import org.springframework.web.socket.messaging.SessionConnectedEvent;
// import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
//
// @RequiredArgsConstructor
// @Component
// @Slf4j
// public class StompHandler implements ChannelInterceptor {
//
// 	@Override
// 	public Message<?> preSend(Message<?> message, MessageChannel channel) {
//
// 		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
// 		// 헤더 토큰 얻기
// 		//String authorizationHeader = String.valueOf(headerAccessor.getNativeHeader("Authorization"));
// 		return message;
// 	}
//
// 	@EventListener
// 	public void handleWebSocketConnectionListener(SessionConnectedEvent event) {
// 		log.info("연결됨: {}", event.toString());
// 	}
//
// 	@EventListener
// 	public void handleWebSocketDisconnectionListener(SessionDisconnectEvent event) {
// 		log.info("연결 해제: {}", event.toString());
// 	}
// }
