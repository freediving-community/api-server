package com.freediving.communityservice.application.port.out;

import com.freediving.communityservice.adapter.out.persistence.chat.ChatRoomUserId;

public interface ChatRoomUserCreationPort {
	Long enterChatRoom(ChatRoomUserId chatRoomUserId, Long lastCheckedMsgId);

	void leaveChatRoom(ChatRoomUserId build);
}
