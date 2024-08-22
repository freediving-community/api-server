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
public class ChatRoomListQueryCommand extends SelfValidating<ChatRoomListQueryCommand> {

	@NotNull
	private final UserProvider userProvider;

	private final ChatType chatType;

	public ChatRoomListQueryCommand(UserProvider userProvider, ChatType chatType) {
		this.userProvider = userProvider;
		this.chatType = chatType;
		this.validateSelf();
	}
}
