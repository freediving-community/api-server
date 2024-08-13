package com.freediving.communityservice.adapter.out.dto.chat;

import lombok.Data;

@Data
public class ChatMessageLasChecked {
	private final Long userId;
	private final Long lastCheckedMsgId;
}
