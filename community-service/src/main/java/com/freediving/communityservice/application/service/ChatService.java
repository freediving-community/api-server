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

	/*
	 * 버디 이벤트로부터 받은 채팅방 입장(없는 경우 생성 후 입장) 처리
	 * */
	@Override
	public ChatResponse requestBuddyChatRoom(ChatRoomCommand command) {

		ChatRoom chatRoom = chatRoomReadPort.getChatRoom(command.getChatType(), command.getTargetId());
		if (chatRoom == null) {
			//TODO: Kafka -> BuddyEvent로부터 받아온 정보를 기반으로 생성 (Plan B. 입장 시점에 Buddy Internal API를 조회)
			String buddyEventInfoTitle = "";
			String buddyEventOpenChatRoomURL = "https://open.kakao.com/o/sHRwpFCg";
			Long chatCreatedBy = 1L;

			chatRoom = chatRoomCreationPort.createChatRoom(
				// TODO: 버디 이벤트 방장 사용자 정보
				chatCreatedBy,
				command.getChatType(),
				command.getTargetId(),
				buddyEventInfoTitle,
				1L,
				buddyEventOpenChatRoomURL
			);

			// return ChatResponse(chatRoom의 정보)
		}

		if (chatRoom.isEnabled()) {
		}

		return null;
	}
}
