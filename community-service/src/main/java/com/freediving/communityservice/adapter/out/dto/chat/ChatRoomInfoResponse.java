package com.freediving.communityservice.adapter.out.dto.chat;

import com.freediving.communityservice.adapter.out.persistence.constant.ChatType;

import lombok.Data;

@Data
public class ChatRoomInfoResponse {
	//ID, 타입, 제목, 참가자수, 활성 상태
	private Long chatRoomId;
	private ChatType chatType;
	private String title;
	private Long participantCount;
	private boolean enabled;
}
