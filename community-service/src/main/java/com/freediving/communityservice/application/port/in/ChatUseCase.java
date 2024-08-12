package com.freediving.communityservice.application.port.in;

import com.freediving.communityservice.adapter.out.dto.chat.ChatRoomResponse;

public interface ChatUseCase {
	ChatRoomResponse requestBuddyChatRoom(ChatRoomCommand command);
}
