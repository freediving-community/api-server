package com.freediving.communityservice.adapter.out.dto.chat;

import com.freediving.communityservice.adapter.out.persistence.constant.ChatType;
import com.freediving.communityservice.domain.ChatRoom;

import lombok.Data;

@Data
public class ChatRoomInfoResponse {
	//ID, 타입, 제목, 참가자수, 활성 상태
	private final Long chatRoomId;
	private final ChatType chatType;
	private final String title;
	private final Long participantCount;
	private final boolean enabled;
	private final Long createdBy;

	public static ChatRoomInfoResponse from(ChatRoom chatRoom) {
		return new ChatRoomInfoResponse(
			chatRoom.getChatRoomId(),
			chatRoom.getChatType(),
			chatRoom.getTitle(),
			chatRoom.getParticipantCount(),
			chatRoom.isEnabled(),
			chatRoom.getCreatedBy()
		);
	}
}
