package com.freediving.communityservice.application.port.out;

import com.freediving.communityservice.adapter.out.persistence.constant.ChatType;
import com.freediving.communityservice.domain.ChatRoom;

public interface ChatRoomCreationPort {
	ChatRoom createChatRoom(Long requestUserId, ChatType chatType, Long buddyEventId, String title,
		Long participantCount,
		String openChatRoomURL);
}
