package com.freediving.communityservice.application.service;

import org.springframework.stereotype.Service;

import com.freediving.communityservice.adapter.out.dto.chat.ChatResponse;
import com.freediving.communityservice.application.port.in.ChatRoomCommand;
import com.freediving.communityservice.application.port.in.ChatUseCase;
import com.freediving.communityservice.application.port.out.ChatRoomCreationPort;
import com.freediving.communityservice.application.port.out.ChatRoomReadPort;
import com.freediving.communityservice.domain.ChatRoom;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ChatService implements ChatUseCase {

	private final ChatRoomReadPort chatRoomReadPort;
	private final ChatRoomCreationPort chatRoomCreationPort;
	// private final ChatRoomDeletePort chatRoomDeletePort;

	@Override
	public ChatResponse requestChatRoom(ChatRoomCommand command) {

		/*
		 *	TODO: Buddy서비스에서 eventId로 참여자 조회하여 유효성 검사
		 *   아닌 경우 거절 후 제거, 없는 경우 생성 후 참여
		 * */

		//TODO: BuddyEvent로부터 받아온 정보를 기반으로 생성
		String buddyEventInfoTitle = "";
		String buddyEventOpenChatRoomURL = "https://kakaotalk.openchat.url/sample";

		ChatRoom chatRoom = chatRoomReadPort.getChatRoom(command.getChatType(), command.getBuddyEventId());
		if (chatRoom == null) {
			chatRoom = chatRoomCreationPort.createBuddyChatRoom(
				// TODO: 버디 이벤트 방장 사용자 정보
				command.getUserProvider().getRequestUserId(),
				command.getChatType(),
				command.getBuddyEventId(),
				buddyEventInfoTitle,
				buddyEventOpenChatRoomURL
			);
		}

		if (chatRoom.isEnabled()) {
		}
		if ("BuddyChatRoomInfo".equals("isValidETC")) {
			// chatRoomDeletePort.markDeleted
			// chatRoomDeletePort.editChatRoom( isEnabled false)
		}

		return null;
	}
}
