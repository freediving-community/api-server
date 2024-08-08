package com.freediving.communityservice.application.port.in;

import com.freediving.communityservice.adapter.out.dto.chat.ChatResponse;

public interface ChatUseCase {
	ChatResponse requestBuddyChatRoom(ChatRoomCommand command);
}
