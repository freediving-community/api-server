package com.freediving.communityservice.adapter.in.web.chat;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.communityservice.adapter.in.dto.ChatMsgWriteRequest;
import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.dto.chat.ChatMsgResponse;
import com.freediving.communityservice.adapter.out.dto.chat.ChatRoomListResponse;
import com.freediving.communityservice.adapter.out.dto.chat.ChatRoomResponse;
import com.freediving.communityservice.adapter.out.persistence.constant.ChatType;
import com.freediving.communityservice.application.port.in.ChatMsgCommand;
import com.freediving.communityservice.application.port.in.ChatRoomCommand;
import com.freediving.communityservice.application.port.in.ChatRoomListQueryCommand;
import com.freediving.communityservice.application.port.in.ChatUseCase;
import com.freediving.communityservice.application.port.in.SseUseCase;
import com.freediving.communityservice.config.type.CacheType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "ChatRoom 채팅방", description = "채팅방 입장, 조회")
@RequestMapping("/v1")
@RestController
public class ChatRoomController {

	private final CacheManager cacheManager;
	private final SseUseCase sseUseCase;
	private final ChatUseCase chatUseCase;
	private final Cache chatRoomCache;

	public ChatRoomController(CacheManager cacheManager, SseUseCase sseUseCase, ChatUseCase chatUseCase) {
		this.cacheManager = cacheManager;
		this.sseUseCase = sseUseCase;
		this.chatUseCase = chatUseCase;
		this.chatRoomCache = cacheManager.getCache(CacheType.CHATROOM_ENTER_TIME.getCacheName());
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

	@Operation(
		summary = "버디 채팅방 입장",
		description = "버디 채팅방 입장 메세지 조회, 기본 15개 메세지 조회",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "채팅방 입장",
				useReturnTypeSchema = true
			)
		}
	)
	@GetMapping("/chat/buddy/{buddyEventId}")
	public ResponseEntity<ResponseJsonObject<ChatRoomResponse>> enterBuddyChatRoom(
		@Parameter(hidden = true) UserProvider userProvider,
		@PathVariable("buddyEventId") Long buddyEventId
	) {
		ChatRoomResponse chatRoomResponse = chatUseCase.enterChatRoom(
			ChatRoomCommand.builder()
				.userProvider(userProvider)
				.chatType(ChatType.BUDDY)
				.targetId(buddyEventId)
				.build()
		);

		String cacheKey = getCacheKey(userProvider.getRequestUserId());
		chatRoomCache.put(cacheKey, chatRoomResponse.getRoomInfo().getChatRoomId());

		return ResponseEntity.ok(new ResponseJsonObject<>(ServiceStatusCode.OK, chatRoomResponse));
	}

	@Operation(
		summary = "입장한 채팅방 목록",
		description = "입장한 채팅방 목록과 읽지 않은 메세지 여부 표시",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "입장한 채팅방 목록",
				useReturnTypeSchema = true
			)
		}
	)
	@GetMapping("/chat/room")
	public ResponseEntity<ResponseJsonObject<List<ChatRoomListResponse>>> enterBuddyChatRoom(
		@Parameter(hidden = true) UserProvider userProvider,
		@Parameter(description = "채팅방 타입") @RequestParam(value = "chatType", required = false, defaultValue = "") ChatType chatType
	) {
		List<ChatRoomListResponse> chatRoomListResponse = chatUseCase.getChatRoomList(
			ChatRoomListQueryCommand.builder()
				.userProvider(userProvider)
				.chatType(chatType)
				.build()
		);

		return ResponseEntity.ok(new ResponseJsonObject<>(ServiceStatusCode.OK, chatRoomListResponse));
	}

	private String getCacheKey(Long userId) {
		return CacheType.CHATROOM_ENTER_TIME.getCacheName() + ":" + userId;
	}

	private String checkChatRoomEntered(UserProvider userProvider, Long chatRoomId) {
		String cacheKey = getCacheKey(userProvider.getRequestUserId());
		Optional<ValueWrapper> cachedValue = Optional.ofNullable(chatRoomCache.get(cacheKey));

		// TODO: 클라이언트 현재 URL로 새로고침
		ValueWrapper enteredRoomId = cachedValue.orElseThrow(() ->
			new BuddyMeException(ServiceStatusCode.BAD_REQUEST, "채팅방에 다시 접속이 필요합니다.")
		);
		if (!chatRoomId.equals(enteredRoomId.get())) {
			throw new BuddyMeException(ServiceStatusCode.BAD_REQUEST, "채팅방에 다시 접속이 필요합니다.");
		}
		return cacheKey;
	}

}
