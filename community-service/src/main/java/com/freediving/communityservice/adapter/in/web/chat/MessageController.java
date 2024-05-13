package com.freediving.communityservice.adapter.in.web.chat;

import java.util.Map;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MessageController {

	private final SimpMessageSendingOperations simpleMessageSendingOperations;

	@EventListener
	public void handleWebSocketConnectListener(SessionConnectEvent event) {
		log.info("Connection 연결됨, event: {}", event);
	}

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccesor = StompHeaderAccessor.wrap(event.getMessage());
		String sessionId = headerAccesor.getSessionId();

		log.info("연결 해제됨, session: {}: ", sessionId);
	}

	@MessageMapping("/chat/{roomId}")
	@SendTo("/chat/{roomId}")
	public void sendMessage(Map<String, Object> params) {

		simpleMessageSendingOperations.convertAndSend("/chat/" + params.get("roomId"), params);
	}
}
