package com.freediving.communityservice.adapter.in.web.chat;

import java.util.List;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.dto.chat.ChatRoomListResponse;
import com.freediving.communityservice.adapter.out.dto.chat.ChatRoomResponse;
import com.freediving.communityservice.adapter.out.persistence.constant.ChatType;
import com.freediving.communityservice.application.port.in.ChatRoomCommand;
import com.freediving.communityservice.application.port.in.ChatRoomListQueryCommand;
import com.freediving.communityservice.application.port.in.ChatUseCase;
import com.freediving.communityservice.config.type.CacheType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "ChatRoom 채팅방", description = "채팅방 입장, 조회")
@RequestMapping("/v1")
@RestController
public class ChatRoomController {

	private final ChatUseCase chatUseCase;
	private final Cache chatRoomCache;

	public ChatRoomController(CacheManager cacheManager, ChatUseCase chatUseCase) {
		this.chatUseCase = chatUseCase;
		this.chatRoomCache = cacheManager.getCache(CacheType.CHATROOM_ENTER_TIME.getCacheName());
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

		String cacheKey = CacheType.CHATROOM_ENTER_TIME.getCacheName() + ":" + userProvider.getRequestUserId();
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

}
