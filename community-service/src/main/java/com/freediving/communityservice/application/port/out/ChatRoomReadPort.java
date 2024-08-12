package com.freediving.communityservice.application.port.out;

import java.util.List;

import com.freediving.communityservice.adapter.out.persistence.constant.ChatType;
import com.freediving.communityservice.domain.ChatRoom;

public interface ChatRoomReadPort {
	ChatRoom getChatRoom(ChatType chatType, Long targetId);

	List<ChatRoom> getChatRoomList(Long createdBy, Long chatRoomId);
}
