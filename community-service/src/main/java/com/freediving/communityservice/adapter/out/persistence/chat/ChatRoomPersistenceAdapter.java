package com.freediving.communityservice.adapter.out.persistence.chat;

import java.util.List;
import java.util.Optional;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.communityservice.adapter.out.persistence.constant.ChatType;
import com.freediving.communityservice.application.port.out.ChatRoomCreationPort;
import com.freediving.communityservice.application.port.out.ChatRoomEditPort;
import com.freediving.communityservice.application.port.out.ChatRoomReadPort;
import com.freediving.communityservice.domain.ChatRoom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ChatRoomPersistenceAdapter implements ChatRoomReadPort, ChatRoomCreationPort, ChatRoomEditPort {

	private final ChatRoomRepository chatRoomRepository;
	private final JPAQueryFactory jpaQueryFactory;
	private final ChatRoomPersistenceMapper chatRoomMapper;
	// private final JdbcClient jdbcClient;

	@Override
	public ChatRoom getChatRoom(ChatType chatType, Long targetId) {
		Optional<ChatRoomJpaEntity> chatRoom = chatRoomRepository.findByChatTypeAndTargetId(chatType, targetId);
		return chatRoom.map(chatRoomMapper::mapToDomain).orElse(null);
	}

	@Override
	public List<ChatRoom> getChatRoomList(Long createdBy, Long chatRoomId) {
		return List.of();
	}

	@Override
	public ChatRoom createChatRoom(Long requestUserId, ChatType chatType, Long buddyEventId, String title,
		Long participantCount,
		String openChatRoomURL) {
		ChatRoomJpaEntity createdChatRoom = chatRoomRepository.save(
			ChatRoomJpaEntity.of(requestUserId, chatType, buddyEventId, title, participantCount, openChatRoomURL)
		);
		return chatRoomMapper.mapToDomain(createdChatRoom);
	}

	@Override
	public ChatRoom editChatRoom(Long requestUserId, ChatType chatType, Long targetId, String title,
		Long participantCount, String openChatRoomURL) {
		Optional<ChatRoomJpaEntity> chatRoom = chatRoomRepository.findByChatTypeAndTargetId(chatType, targetId);
		ChatRoomJpaEntity foundChatRoom = chatRoom.orElseThrow(
			() -> new BuddyMeException(ServiceStatusCode.BAD_REQUEST));

		foundChatRoom.editChatRoomInfo(title, participantCount, openChatRoomURL);
		chatRoomRepository.save(foundChatRoom);

		return chatRoomMapper.mapToDomain(foundChatRoom);
	}
}
