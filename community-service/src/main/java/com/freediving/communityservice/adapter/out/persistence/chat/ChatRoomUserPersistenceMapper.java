package com.freediving.communityservice.adapter.out.persistence.chat;

import org.springframework.stereotype.Component;

import com.freediving.communityservice.domain.ChatRoomUser;

@Component
public class ChatRoomUserPersistenceMapper {
	public ChatRoomUser mapToDomain(ChatRoomUserJpaEntity chatRoomUserJpaEntity) {
		return ChatRoomUser.builder()
			.chatRoomId(chatRoomUserJpaEntity.getId().getChatRoomId())
			.userId(chatRoomUserJpaEntity.getId().getUserId())
			.createdAt(chatRoomUserJpaEntity.getCreatedAt())
			.lastCheckedMsgId(chatRoomUserJpaEntity.getLastCheckedMsgId())
			.build();
	}
}
