package com.freediving.communityservice.application.port.out;

import java.util.List;

import com.freediving.communityservice.adapter.out.dto.chat.ChatRoomListResponse;
import com.freediving.communityservice.adapter.out.persistence.constant.ChatType;
import com.freediving.communityservice.domain.ChatRoom;

public interface ChatRoomReadPort {
	ChatRoom getChatRoom(ChatType chatType, Long targetId);

	List<ChatRoomListResponse> getChatRoomList(Long userId, ChatType chatType);
}
