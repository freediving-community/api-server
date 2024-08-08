package com.freediving.communityservice.adapter.out.dto.chat;

import java.util.List;

import com.freediving.communityservice.adapter.out.dto.user.UserInfo;

import lombok.Data;

@Data
public class ChatRoomResponse {
	/*
	 * 채팅방: ID, 타입, 제목, 참가자수, 활성 상태
	 * 채팅방-참가자: [ UserInfo 정보, ...]
	 * 메세지: [ {ID,작성자,내용,시간}, {}... ]
	 * 버디이벤트: 장소, 시간, 단톡 URL
	 * */
	private ChatRoomInfoResponse roomInfo;
	private List<UserInfo> users;
	private List<ChatMessageResponse> messages;
	private ChatRoomBuddyEventInfo buddyEventInfo;
}
