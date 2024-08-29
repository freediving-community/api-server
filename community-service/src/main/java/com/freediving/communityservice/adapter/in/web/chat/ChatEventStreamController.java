package com.freediving.communityservice.adapter.in.web.chat;

import java.util.Optional;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.communityservice.adapter.in.dto.ChatMsgWriteRequest;
import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.dto.chat.ChatMsgResponse;
import com.freediving.communityservice.application.port.in.ChatMsgCommand;
import com.freediving.communityservice.application.port.in.ChatUseCase;
import com.freediving.communityservice.application.port.in.SseUseCase;
import com.freediving.communityservice.config.type.CacheType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "채팅방 접속 후 실시간 수신 구독", description = "채팅방 접속 후 실시간 채팅 내용 SSE 연결")
@RequestMapping("/v1")
@RestController
public class ChatEventStreamController {

	private final SseUseCase sseUseCase;
	private final ChatUseCase chatUseCase;
	private final Cache chatRoomCache;

	public ChatEventStreamController(SseUseCase sseUseCase, ChatUseCase chatUseCase, CacheManager cacheManager) {
		this.sseUseCase = sseUseCase;
		this.chatUseCase = chatUseCase;
		this.chatRoomCache = cacheManager.getCache(CacheType.CHATROOM_ENTER_TIME.getCacheName());
	}

	/*
	 * 메세지 수신에 관하여 SSE 구독.
	 * 메세지 전송은 ChatRoomController 에서 처리함.
	 * 1. 클라이언트 자신의 메세지도 여기서 처리
	 * 2. 클라이언트 본인외 다른 사용자의 Online-Offline을 관리하여 채팅방에 접속 상태 표기
	 * */
	@Operation(
		summary = "채팅방 입장 후 EventStream 구독",
		description = "채팅방 입장 완료 후, EventStream 관리. 현재 접속한 사용자 정보 전달",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "채팅방 SSE 연결 성공",
				useReturnTypeSchema = true
			)
		}
	)
	@GetMapping(value = "/chat/sub/{chatRoomId}", produces = "text/event-stream")
	public SseEmitter subscribeChatRoom(
		@Parameter(hidden = true) UserProvider userProvider,
		@PathVariable("chatRoomId") Long chatRoomId,
		@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId
	) {
		return sseUseCase.subsChatRoom(chatRoomId, userProvider.getRequestUserId(), lastEventId);
	}

	@GetMapping(value = "/chat/close/{chatRoomId}")
	public ResponseEntity<ResponseJsonObject<ChatMsgResponse>> closeChatRoom(
		@Parameter(hidden = true) UserProvider userProvider,
		@PathVariable("chatRoomId") Long chatRoomId
	) {
		sseUseCase.closeChatRoom(chatRoomId, userProvider.getRequestUserId());
		return ResponseEntity.ok(
			new ResponseJsonObject<>(ServiceStatusCode.OK, ChatMsgResponse.builder().msg("closed").build()));
	}

	@Operation(
		summary = "채팅방 메세지 전송",
		description = "채팅방 메세지 전송: 채팅방 입장, 메세지 전송 20분 초과시 재접속 필요",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "채팅 메세지 전송",
				useReturnTypeSchema = true
			)
		}
	)
	@PostMapping("/chat/send/{chatRoomId}")
	public ResponseEntity<ResponseJsonObject<ChatMsgResponse>> sendChatMsg(
		@Parameter(hidden = true) UserProvider userProvider,
		@PathVariable("chatRoomId") Long chatRoomId,
		@RequestBody ChatMsgWriteRequest chatMsgWriteRequest
	) {
		String cacheKey = checkChatRoomEntered(userProvider, chatRoomId);
		ChatMsgResponse chatMsgResponse = chatUseCase.newChatMsg(
			ChatMsgCommand.builder()
				.chatRoomId(chatRoomId)
				.msg(chatMsgWriteRequest.getMsg())
				.msgType(chatMsgWriteRequest.getMsgType())
				.replyToMsgId(chatMsgWriteRequest.getReplyToMsgId())
				.createdBy(userProvider.getRequestUserId())
				.build()
		);
		sseUseCase.broadcastMsg(chatRoomId, chatMsgResponse);

		// 입장 시간 갱신
		chatRoomCache.put(cacheKey, chatRoomId);
		return ResponseEntity.ok(new ResponseJsonObject<>(ServiceStatusCode.OK, chatMsgResponse));
	}

	private String checkChatRoomEntered(UserProvider userProvider, Long chatRoomId) {
		String cacheKey = CacheType.CHATROOM_ENTER_TIME.getCacheName() + ":" + userProvider.getRequestUserId();
		Optional<Cache.ValueWrapper> cachedValue = Optional.ofNullable(chatRoomCache.get(cacheKey));

		// TODO: 클라이언트 현재 URL로 새로고침
		Cache.ValueWrapper enteredRoomId = cachedValue.orElseThrow(() ->
			new BuddyMeException(ServiceStatusCode.BAD_REQUEST, "채팅방에 다시 접속이 필요합니다.")
		);
		if (!chatRoomId.equals(enteredRoomId.get())) {
			throw new BuddyMeException(ServiceStatusCode.BAD_REQUEST, "채팅방에 다시 접속이 필요합니다.");
		}
		return cacheKey;
	}

}
