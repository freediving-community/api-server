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
		log.info("lastEventId >>> {}", lastEventId);
		SseEmitter emitter = sseManager.registerEmitter(chatRoomId, requestUserId);

		broadcastOnlineUser(chatRoomId);
		log.info("subs >>> chatRoomId:{}, userId:{}", chatRoomId, requestUserId);
		return emitter;
	}

	@Override
	public void closeChatRoom(Long chatRoomId, Long requestUserId) {
		sseManager.get(chatRoomId).get(requestUserId).complete();
		sseManager.get(chatRoomId).remove(requestUserId);
		broadcastOnlineUser(chatRoomId);
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

	private void broadcastOnlineUser(Long chatRoomId) {
		List<Long> onlineUsers = sseManager.get(chatRoomId).keySet().stream().toList();
		sseManager.get(chatRoomId).values().forEach(
			(e) -> {
				sseManager.sendMsg(e, ChatSseManager.EVENT_TYPE_SYSTEM, onlineUsers);
			}
		);
	}
}
