package com.freediving.communityservice.application.port.out;

import com.freediving.communityservice.adapter.out.persistence.constant.ChatType;
import com.freediving.communityservice.domain.ChatRoom;

public interface ChatRoomCreationPort {
	ChatRoom createBuddyChatRoom(Long requestUserId, ChatType chatType, Long buddyEventId, String title,
		String openChatRoomURL);
}
