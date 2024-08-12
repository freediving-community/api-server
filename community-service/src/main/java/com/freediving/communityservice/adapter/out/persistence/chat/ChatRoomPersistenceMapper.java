package com.freediving.communityservice.adapter.out.persistence.chat;

import org.springframework.stereotype.Component;

import com.freediving.communityservice.domain.ChatRoom;

@Component
public class ChatRoomPersistenceMapper {
	public ChatRoom mapToDomain(ChatRoomJpaEntity chatRoomJpaEntity) {
		return ChatRoom.builder()
			.chatRoomId(chatRoomJpaEntity.getChatRoomId())
			.chatType(chatRoomJpaEntity.getChatType())
			.targetId(chatRoomJpaEntity.getTargetId())
			.title(chatRoomJpaEntity.getTitle())
			.participantCount(chatRoomJpaEntity.getParticipantCount())
			.openChatRoomURL(chatRoomJpaEntity.getOpenChatRoomURL())
			.enabled(chatRoomJpaEntity.isEnabled())
			.deletedAt(chatRoomJpaEntity.getDeletedAt())
			.createdAt(chatRoomJpaEntity.getCreatedAt())
			.createdBy(chatRoomJpaEntity.getCreatedBy())
			.modifiedAt(chatRoomJpaEntity.getModifiedAt())
			.modifiedBy(chatRoomJpaEntity.getModifiedBy())
			.build();
	}
}
