package com.freediving.communityservice.application.port.in;

import com.freediving.communityservice.adapter.out.dto.chat.ChatRoomResponse;

public interface ChatUseCase {
	ChatRoomResponse handleBuddyChatRoom(BuddyChatRoomCommand command);

	ChatRoomResponse requestChatRoom(ChatRoomCommand command);
}
