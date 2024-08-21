package com.freediving.communityservice.adapter.out.dto.chat;

import lombok.Data;

@Data
public class ChatMessageLastChecked {
	private final Long userId;
	private final Long lastCheckedMsgId;
}
