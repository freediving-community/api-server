package com.freediving.communityservice.adapter.out.persistence.chat;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.http.MediaType;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling
@Component
public class ChatSseManager {
	public static final String EVENT_TYPE_CHAT = "chat";
	public static final String EVENT_TYPE_SYSTEM = "system";
	private static final Long SESSION_TIMEOUT = 20 * 60L * 1000;
	private static final ConcurrentMap<Long, ConcurrentMap<Long, SseEmitter>> emitters = new ConcurrentHashMap<>();

	private static final Duration delay = Duration.ofSeconds(5L); // 30초 간격
	private final ThreadPoolTaskScheduler taskScheduler;
	private Runnable heartbeatTask;

	public ChatSseManager(TaskScheduler taskScheduler) {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setThreadNamePrefix("SseHeartbeat-");
		scheduler.setPoolSize(1);
		scheduler.initialize();
		this.taskScheduler = scheduler;
	}

	public SseEmitter registerEmitter(Long chatRoomId, Long requestUserId) {
		// 요청 들어온 TCP(Response) 연결을 유지
		SseEmitter emitter = new SseEmitter(SESSION_TIMEOUT);

		// 연결 종료 시 실행될 Function 등록
		emitter.onCompletion(() -> remove(chatRoomId, requestUserId));
		emitter.onError((e) -> remove(chatRoomId, requestUserId));
		emitter.onTimeout(() -> remove(chatRoomId, requestUserId));

		// chatRoomId로 해당 채팅방 Emitter Map 에 추가한다. 없는 경우 신규 생성 후 추가
		emitters.computeIfAbsent(chatRoomId, k -> new ConcurrentHashMap<>()).put(requestUserId, emitter);
		startScheduledTask();

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
				.id(LocalDateTime.now().toString())
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
		if (!emitters.containsKey(chatRoomId)) {
			throw new BuddyMeException(ServiceStatusCode.BAD_REQUEST, "채팅방에 다시 접속이 필요합니다.");
		}
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

	/*
	 * 클라이언트 SSE 유지를 위한 heartbeat 관리 스케줄러
	 * */
	private void startScheduledTask() {
		if (heartbeatTask == null || taskScheduler.getScheduledExecutor().isShutdown()) {
			heartbeatTask = () -> {
				if (!emitters.isEmpty()) {
					log.info("schedule Start");
					sendHeartbeat();
				} else {
					stopScheduledTask();
				}
			};
			taskScheduler.scheduleAtFixedRate(heartbeatTask, delay);
		}

		// if (heartbeatTask == null) {
		// 	heartbeatTask = () -> {
		// 		if (!emitters.isEmpty()) {
		// 			log.info("schedule Start");
		// 			sendHeartbeat();
		// 		} else {
		// 			stopScheduledTask();
		// 		}
		// 	};
		// 	taskScheduler.scheduleAtFixedRate(heartbeatTask, delay);
		// } else if (taskScheduler.getScheduledExecutor().isShutdown()) {
		// 	taskScheduler.scheduleAtFixedRate(heartbeatTask, delay);
		// }
	}

	private void stopScheduledTask() {
		if (heartbeatTask != null) {
			taskScheduler.shutdown();
			// taskScheduler.stop();
			heartbeatTask = null;
			log.info("Schedule shutdown. emitters:{}", emitters.size());
		}
	}

	private void sendHeartbeat() {
		emitters.values().forEach(room -> {
			room.values().forEach(sseEmitter -> {
					try {
						SseEmitter.SseEventBuilder sseEventBuilder = SseEmitter.event()
							.id("id")
							.name(EVENT_TYPE_SYSTEM)
							.data("1", MediaType.APPLICATION_JSON)
							.comment("heartbeat");
						sseEmitter.send(sseEventBuilder);
						log.info("schedule sendHeartbeat, sseEmitter:{}", sseEmitter.hashCode());
					} catch (IOException e) {
						log.error("fail >> Server-Sent:sendHeartbeat >>> {} \n eventType >>> {} \n message >>> {} ",
							e.getMessage(), EVENT_TYPE_SYSTEM, "1");
						sseEmitter.completeWithError(e);
					}
				}
			);
		});
	}

}
