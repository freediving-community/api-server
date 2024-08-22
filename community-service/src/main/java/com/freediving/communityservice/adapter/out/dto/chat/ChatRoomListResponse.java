package com.freediving.communityservice.adapter.out.dto.chat;

import com.freediving.communityservice.adapter.out.persistence.constant.ChatType;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChatRoomListResponse {
	/*
	 * 채팅방 목록
	 * */
	private final Long chatRoomId;
	private final ChatType chatType;
	private final String title;
	private final Long participantCount;
	private final boolean enabled;
	private final Long createdBy;
	private final boolean hasNew;
}
