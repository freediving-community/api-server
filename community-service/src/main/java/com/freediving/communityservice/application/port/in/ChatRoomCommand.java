package com.freediving.communityservice.application.port.in;

import com.freediving.common.SelfValidating;
import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.persistence.constant.ChatType;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class ChatRoomCommand extends SelfValidating<ChatRoomCommand> {

	@NotNull
	private final UserProvider userProvider;

	@NotNull
	private final ChatType chatType;

	private final Long targetId;

	public ChatRoomCommand(UserProvider userProvider, ChatType chatType, Long targetId) {
		this.userProvider = userProvider;
		this.chatType = chatType;
		this.targetId = targetId;
		this.validateSelf();
	}
}
