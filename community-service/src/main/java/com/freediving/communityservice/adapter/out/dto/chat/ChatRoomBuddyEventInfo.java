package com.freediving.communityservice.adapter.out.dto.chat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatRoomBuddyEventInfo {
	private final Long buddyEventId;
	private final String title;
	private final String openChatRoomURL;
	private final Long createdBy;
}
