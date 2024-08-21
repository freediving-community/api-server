package com.freediving.communityservice.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.freediving.communityservice.adapter.out.dto.chat.ChatMsgResponse;
import com.freediving.communityservice.adapter.out.persistence.chat.ChatSseManager;
import com.freediving.communityservice.application.port.in.SseUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatEventStreamService implements SseUseCase {
	private final ChatSseManager sseManager;

	@Override
	public SseEmitter subsChatRoom(Long chatRoomId, Long requestUserId, String lastEventId) {

		SseEmitter emitter = sseManager.registerEmitter(chatRoomId, requestUserId);

		sendOnlineUser(emitter, chatRoomId);
		log.info("subs >>> chatRoomId:{}, userId:{}", chatRoomId, requestUserId);
		return emitter;
	}

	@Override
	public void broadcastMsg(Long chatRoomId, ChatMsgResponse chatMsgResponse) {
		sseManager.get(chatRoomId).values().forEach(
			(e) -> {
				sendMsg(e, chatMsgResponse);
			}
		);
	}

	private void sendMsg(SseEmitter sseEmitter, ChatMsgResponse chatMsgResponse) {
		sseManager.sendMsg(sseEmitter, ChatSseManager.EVENT_TYPE_CHAT, chatMsgResponse);
	}

	private void sendOnlineUser(SseEmitter sseEmitter, Long chatRoomId) {
		List<Long> onlineUsers = sseManager.get(chatRoomId).keySet().stream().toList();
		sseManager.sendMsg(sseEmitter, ChatSseManager.EVENT_TYPE_SYSTEM, onlineUsers);
	}
}
