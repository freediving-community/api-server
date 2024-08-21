package com.freediving.communityservice.adapter.out.persistence.chat;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ChatMsgRepository extends JpaRepository<ChatMsgJpaEntity, Long> {

	// chatRoomId를 기준으로 msgId 내림차순으로 정렬하여 특정 개수의 메시지를 가져오는 메서드
	Optional<List<ChatMsgJpaEntity>> findByChatRoomIdOrderByMsgIdDesc(Long chatRoomId, Pageable pageable);

	// chatRoomId와 lastMsgId를 기준으로 msgId 내림차순으로 정렬하여 특정 개수의 메시지를 가져오는 메서드
	Optional<List<ChatMsgJpaEntity>> findByChatRoomIdAndMsgIdLessThanOrderByMsgIdDesc(
		@Param("chatRoomId") Long chatRoomId,
		@Param("lastMsgId") Long lastMsgId,
		Pageable pageable);

}
