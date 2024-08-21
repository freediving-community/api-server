package com.freediving.communityservice.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatRoomUser {
	private final Long chatRoomId;

	private final Long userId;

	private final LocalDateTime createdAt;

	private final Long lastCheckedMsgId;
}
