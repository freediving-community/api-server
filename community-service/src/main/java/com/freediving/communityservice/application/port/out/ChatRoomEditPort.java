package com.freediving.communityservice.application.port.out;

import com.freediving.communityservice.adapter.out.persistence.constant.ChatType;
import com.freediving.communityservice.domain.ChatRoom;

public interface ChatRoomEditPort {
	ChatRoom editChatRoom(Long requestUserId, ChatType chatType, Long targetId, String title,
		Long participantCount, String openChatRoomURL);
}
