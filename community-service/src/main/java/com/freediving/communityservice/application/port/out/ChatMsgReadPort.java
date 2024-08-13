package com.freediving.communityservice.application.port.out;

import java.util.List;

import com.freediving.communityservice.adapter.out.dto.chat.ChatMsgResponse;

public interface ChatMsgReadPort {
	List<ChatMsgResponse> getRecentMsg(Long chatRoomId);

	List<ChatMsgResponse> getMsgAfterId(Long chatRoomId, Long lastMsgId);
}
