package com.freediving.communityservice.application.port.out;

import java.util.List;

import com.freediving.communityservice.domain.ChatRoomUser;

public interface ChatRoomUserReadPort {

	// getUserById
	List<ChatRoomUser> getAllUserByRoomId(Long chatRoomId);

	List<ChatRoomUser> getAllRoomByUserId(Long userId);

}
