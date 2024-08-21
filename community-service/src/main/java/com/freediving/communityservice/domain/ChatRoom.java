package com.freediving.communityservice.domain;

import java.time.LocalDateTime;

import com.freediving.communityservice.adapter.out.persistence.constant.ChatType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatRoom {
	private final Long chatRoomId;

	private final ChatType chatType;

	private final Long targetId;

	private final String title;

	private final Long participantCount;

	private final String openChatRoomURL;

	private final boolean enabled;

	private final LocalDateTime deletedAt;

	private final LocalDateTime createdAt;

	private final Long createdBy;

	private final LocalDateTime modifiedAt;

	private final Long modifiedBy;

}
