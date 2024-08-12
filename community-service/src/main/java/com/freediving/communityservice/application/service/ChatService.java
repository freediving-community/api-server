package com.freediving.communityservice.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.freediving.communityservice.adapter.out.dto.chat.ChatRoomInfoResponse;
import com.freediving.communityservice.adapter.out.dto.chat.ChatRoomResponse;
import com.freediving.communityservice.adapter.out.persistence.chat.ChatRoomUserId;
import com.freediving.communityservice.adapter.out.persistence.chat.ChatRoomUserJpaEntity;
import com.freediving.communityservice.adapter.out.persistence.chat.ChatRoomUserRepository;
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

	private final ChatRoomUserRepository chatRoomUserRepository;
	// private final ChatRoomUserReadPort chatRoomUserReadPort;
	// private final ChatRoomUserCreationPort chatRoomUserCreationPort;
	// private final ChatRoomDeletePort chatRoomDeletePort;

	/*
	 * 버디 이벤트로부터 받은 채팅방 입장(없는 경우 생성 후 입장) 처리
	 * */
	@Override
	public ChatRoomResponse requestBuddyChatRoom(ChatRoomCommand command) {

		ChatRoom chatRoom = chatRoomReadPort.getChatRoom(command.getChatType(), command.getTargetId());
		if (chatRoom == null) {
			//TODO: Kafka -> BuddyEvent로부터 받아온 정보를 기반으로 생성 (Plan B. 입장 시점에 Buddy Internal API를 조회)
			String buddyEventInfoTitle = "보리보리의 파라다이브 24-08-10 11시 모임";
			String buddyEventOpenChatRoomURL = "https://open.kakao.com/o/sHRwpFCg";
			Long chatRoomCreatedBy = 1L;

			chatRoom = chatRoomCreationPort.createChatRoom(
				// TODO: 버디 이벤트 방장 사용자 정보
				chatRoomCreatedBy,
				command.getChatType(),
				command.getTargetId(),
				buddyEventInfoTitle,
				1L,
				buddyEventOpenChatRoomURL);

			// return ChatRoomResponse(chatRoom의 정보)
		}

		chatRoom.isEnabled(); // 비활성화 시 Type별 수행
		//TODO: 해당 채팅방에 속해 있는지 확인
		//TODO: 그 머냐, Port로 빼서 userList는 List<Long> 으로 주도록 하자. 해도 되나?
		// ChatRoomUserJpaEntity roomUser = chatRoomUserRepository.save(
		// 	new ChatRoomUserJpaEntity(new ChatRoomUserId(111L, 222L), null));
		// chatRoomUserRepository.flush();
		// Optional<List<ChatRoomUserJpaEntity>> t0 = chatRoomUserRepository.findAllById(new ChatRoomUserId(111L, 222L));
		// Optional<List<ChatRoomUserJpaEntity>> t1 = chatRoomUserRepository.findAllById_ChatRoomId(111L);
		// Optional<List<ChatRoomUserJpaEntity>> t2 = chatRoomUserRepository.findAllById_UserId(222L);

		return new ChatRoomResponse(ChatRoomInfoResponse.from(chatRoom), null, null, null);
	}
}
