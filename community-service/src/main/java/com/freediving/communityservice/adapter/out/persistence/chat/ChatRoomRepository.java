package com.freediving.communityservice.adapter.out.persistence.chat;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freediving.communityservice.adapter.out.persistence.constant.ChatType;

public interface ChatRoomRepository extends JpaRepository<ChatRoomJpaEntity, Long> {
	Optional<ChatRoomJpaEntity> findByChatTypeAndTargetId(ChatType chatType, Long targetId);
}
