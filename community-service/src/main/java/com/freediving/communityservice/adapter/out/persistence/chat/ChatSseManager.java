package com.freediving.communityservice.adapter.out.persistence.chat;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ChatSseManager {
	private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 20;
	public static final String EVENT_TYPE_CHAT = "chat";
	public static final String EVENT_TYPE_SYSTEM = "system";

	private static final ConcurrentMap<Long, ConcurrentMap<Long, SseEmitter>> emitters = new ConcurrentHashMap<>();

	public SseEmitter registerEmitter(Long chatRoomId, Long requestUserId) {
		// 요청 들어온 TCP(Response) 연결을 유지
		SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

		// 연결 종료 시 실행될 Function 등록
		emitter.onCompletion(() -> remove(chatRoomId, requestUserId));
		emitter.onError((e) -> remove(chatRoomId, requestUserId));
		emitter.onTimeout(() -> remove(chatRoomId, requestUserId));

		// chatRoomId로 유저의 Emitter Map 을 가져온다. 없는 경우 신규 생성 및 추가
		emitters.computeIfAbsent(chatRoomId, k -> new ConcurrentHashMap<>()).put(requestUserId, emitter);

		return emitter;
	}

	/*
	 * id 는 SSE 식별값
	 * name은 Client의 EventListner의 타겟 이벤트 명
	 * data는 JSON Stringify 하여 전송.
	 * */
	public void sendMsg(SseEmitter sseEmitter, String eventType, Object message) {
		try {
			SseEmitter.SseEventBuilder sseEventBuilder = SseEmitter.event()
				.id("id")
				.name(eventType)
				.data(message, MediaType.APPLICATION_JSON);
			sseEmitter.send(sseEventBuilder);
		} catch (IOException e) {
			log.error("fail >> Server-Sent:sendMsg >>> {} \n eventType >>> {} \n message >>> {} ", e.getMessage(),
				eventType, message.toString());
			sseEmitter.completeWithError(e);
		}
	}

	public ConcurrentMap<Long, SseEmitter> get(Long chatRoomId) {
		return emitters.get(chatRoomId);
	}

	private static void remove(Long chatRoomId, Long requestUserId) {
		ConcurrentMap<Long, SseEmitter> userEmitterMap = emitters.get(chatRoomId);
		if (userEmitterMap != null) {
			userEmitterMap.remove(requestUserId);

			if (userEmitterMap.isEmpty()) {
				emitters.remove(chatRoomId);
			}
		}
	}
}
