package com.freediving.communityservice.adapter.out.dto.chat;

import java.util.List;

import com.freediving.communityservice.adapter.out.dto.user.UserInfo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChatRoomResponse {
	/*
	 * 채팅방: ID, 타입, 제목, 참가자수, 활성 상태
	 * 채팅방-참가자: [ UserInfo 정보, ...]
	 * 메세지: [ {ID,작성자,내용,시간}, {}... ]
	 * 버디이벤트: 장소, 시간, 단톡 URL
	 * */
	private final ChatRoomInfoResponse roomInfo;
	private final List<UserInfo> users;
	private final List<ChatMsgResponse> messages;
	private final ChatRoomBuddyEventInfo buddyEventInfo;
	private final List<ChatMessageLastChecked> chatMessageLastCheckedList;
}
