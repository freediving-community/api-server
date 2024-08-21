package com.freediving.communityservice.adapter.in.web.chat;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.application.port.in.SseUseCase;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "채팅방 접속 후 실시간 수신 구독", description = "채팅방 접속 후 실시간 채팅 내용 SSE 연결")
@RequestMapping("/v1")
@RequiredArgsConstructor
@RestController
public class ChatEventStreamController {

	private final SseUseCase sseUseCase;

	/*
	 * 메세지 수신에 관하여 SSE 구독.
	 * 메세지 전송은 ChatRoomController 에서 처리함.
	 * 1. 클라이언트 자신의 메세지도 여기서 처리
	 * 2. 클라이언트 본인외 다른 사용자의 Online-Offline을 관리하여 채팅방에 접속 상태 표기
	 * */
	@GetMapping(value = "/chat/sub/{chatRoomId}", produces = "text/event-stream")
	public SseEmitter subscribeChatRoom(
		@Parameter(hidden = true) UserProvider userProvider,
		@PathVariable("chatRoomId") Long chatRoomId,
		@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId
	) {
		return sseUseCase.subsChatRoom(chatRoomId, userProvider.getRequestUserId(), lastEventId);
	}
}
