package com.freediving.communityservice.application.port.in;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.freediving.communityservice.adapter.out.dto.chat.ChatMsgResponse;

public interface SseUseCase {

	SseEmitter subsChatRoom(Long chatRoomId, Long requestUserId, String lastEventId);

	void broadcastMsg(Long chatRoomId, ChatMsgResponse chatMsgResponse);
}
