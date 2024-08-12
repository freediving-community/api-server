package com.freediving.communityservice.adapter.out.persistence.chat;

import java.util.List;
import java.util.Optional;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.communityservice.adapter.out.persistence.constant.ChatType;
import com.freediving.communityservice.application.port.out.ChatRoomCreationPort;
import com.freediving.communityservice.application.port.out.ChatRoomReadPort;
import com.freediving.communityservice.domain.ChatRoom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ChatRoomPersistenceAdapter implements ChatRoomReadPort, ChatRoomCreationPort {

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

}
