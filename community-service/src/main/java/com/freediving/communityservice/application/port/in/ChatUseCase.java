package com.freediving.communityservice.application.port.in;

import com.freediving.communityservice.adapter.out.dto.chat.ChatMsgResponse;
import com.freediving.communityservice.adapter.out.dto.chat.ChatRoomResponse;

public interface ChatUseCase {

	ChatRoomResponse enterChatRoom(ChatRoomCommand command);

	ChatMsgResponse newChatMsg(ChatMsgCommand command);
}
