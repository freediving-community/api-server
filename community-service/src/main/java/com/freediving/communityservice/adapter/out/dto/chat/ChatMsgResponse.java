package com.freediving.communityservice.adapter.out.dto.chat;

import java.time.LocalDateTime;

import com.freediving.communityservice.adapter.out.persistence.chat.ChatMsgJpaEntity;
import com.freediving.communityservice.adapter.out.persistence.constant.MsgType;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChatMsgResponse {

	private final Long msgId;

	private final String msg;

	private final MsgType msgType;

	private final Long replyToMsgId;

	private final LocalDateTime createdAt;

	private final Long createdBy;

	public static ChatMsgResponse from(ChatMsgJpaEntity chatMsgJpaEntity) {
		return ChatMsgResponse.builder()
			.msgId(chatMsgJpaEntity.getMsgId())
			.msg(chatMsgJpaEntity.getMsg())
			.msgType(chatMsgJpaEntity.getMsgType())
			.replyToMsgId(chatMsgJpaEntity.getReplyToMsgId())
			.createdAt(chatMsgJpaEntity.getCreatedAt())
			.createdBy(chatMsgJpaEntity.getCreatedBy())
			.build();
	}

}
