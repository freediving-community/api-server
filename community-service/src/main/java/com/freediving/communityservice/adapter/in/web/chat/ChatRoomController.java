package com.freediving.communityservice.adapter.in.web.chat;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.dto.chat.ChatRoomResponse;
import com.freediving.communityservice.adapter.out.persistence.constant.ChatType;
import com.freediving.communityservice.application.port.in.ChatRoomCommand;
import com.freediving.communityservice.application.port.in.ChatUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "ChatRoom 채팅방", description = "채팅 일괄 API")
@RequiredArgsConstructor
@RequestMapping("/v1")
@RestController
public class ChatRoomController {

	private final ChatUseCase chatUseCase;

	@Operation(
		summary = "버디 채팅방 요청 (조회 및 생성)",
		description = "채팅방 입장, 생성이 유효한 조건에서 없는 경우 생성하면서 응답",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "채팅방 입장",
				useReturnTypeSchema = true
			)
		}
	)
	@GetMapping("/chat/buddy/{buddyEventId}")
	public ResponseEntity<ResponseJsonObject<ChatRoomResponse>> requestBuddyChatRoom(
		@Parameter(hidden = true) UserProvider userProvider,
		@PathVariable("buddyEventId") Long buddyEventId
	) {
		//TODO: Buddy Service에서 카프카로 생성시 변경 예정
		ChatRoomResponse chatRoom = chatUseCase.requestBuddyChatRoom(
			ChatRoomCommand.builder()
				.userProvider(userProvider)
				.chatType(ChatType.BUDDY)
				.targetId(buddyEventId)
				.build()
		);
		return ResponseEntity.ok(new ResponseJsonObject<>(ServiceStatusCode.OK, chatRoom));
	}
}
