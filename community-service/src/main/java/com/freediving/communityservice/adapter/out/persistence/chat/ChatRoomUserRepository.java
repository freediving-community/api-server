package com.freediving.communityservice.adapter.out.persistence.chat;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUserJpaEntity, ChatRoomUserId> {
	Optional<List<ChatRoomUserJpaEntity>> findAllById(ChatRoomUserId chatRoomUserId);

	Optional<List<ChatRoomUserJpaEntity>> findAllById_ChatRoomId(Long chatRoomId);

	Optional<List<ChatRoomUserJpaEntity>> findAllById_UserId(Long userId);
}
