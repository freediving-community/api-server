package com.freediving.communityservice.application.port.in;

import java.util.List;

import com.freediving.communityservice.adapter.out.dto.chat.ChatMsgResponse;
import com.freediving.communityservice.adapter.out.dto.chat.ChatRoomListResponse;
import com.freediving.communityservice.adapter.out.dto.chat.ChatRoomResponse;

public interface ChatUseCase {

	ChatRoomResponse enterChatRoom(ChatRoomCommand command);

	ChatMsgResponse newChatMsg(ChatMsgCommand command);

	List<ChatRoomListResponse> getChatRoomList(ChatRoomListQueryCommand command);
}
