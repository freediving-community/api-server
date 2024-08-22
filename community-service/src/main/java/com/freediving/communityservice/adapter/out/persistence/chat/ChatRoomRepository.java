package com.freediving.communityservice.adapter.out.persistence.chat;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoomJpaEntity, Long> {
}
