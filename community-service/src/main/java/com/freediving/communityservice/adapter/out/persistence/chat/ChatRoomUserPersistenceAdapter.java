package com.freediving.communityservice.adapter.out.persistence.chat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.communityservice.application.port.out.ChatRoomUserCreationPort;
import com.freediving.communityservice.application.port.out.ChatRoomUserReadPort;
import com.freediving.communityservice.domain.ChatRoomUser;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ChatRoomUserPersistenceAdapter implements ChatRoomUserCreationPort, ChatRoomUserReadPort {

	private final ChatRoomUserRepository chatRoomUserRepository;
	private final ChatRoomUserPersistenceMapper chatRoomUserMapper;
	// Optional<List<ChatRoomUserJpaEntity>> t0 = chatRoomUserRepository.findAllById(new ChatRoomUserId(111L, 222L));

	@Override
	public Long enterChatRoom(ChatRoomUserId chatRoomUserId, Long lastCheckedMsgId) {
		ChatRoomUserJpaEntity roomUser = chatRoomUserRepository.save(
			new ChatRoomUserJpaEntity(chatRoomUserId, LocalDateTime.now(), lastCheckedMsgId)
		);
		return 0L;
	}

	@Override
	public List<ChatRoomUser> getAllUserByRoomId(Long chatRoomId) {
		Optional<List<ChatRoomUserJpaEntity>> chatRoomUserJpaEntities = chatRoomUserRepository.findAllById_ChatRoomId(
			chatRoomId);
		List<ChatRoomUserJpaEntity> chatRoomUsers = chatRoomUserJpaEntities.orElse(List.of());
		return chatRoomUsers.stream().map(chatRoomUserMapper::mapToDomain).toList();
	}

	@Override
	public List<ChatRoomUser> getAllRoomByUserId(Long userId) {
		Optional<List<ChatRoomUserJpaEntity>> chatRoomUserJpaEntities = chatRoomUserRepository.findAllById_UserId(
			userId);
		List<ChatRoomUserJpaEntity> chatRoomUsers = chatRoomUserJpaEntities.orElse(List.of());
		return chatRoomUsers.stream().map(chatRoomUserMapper::mapToDomain).toList();
	}
}
