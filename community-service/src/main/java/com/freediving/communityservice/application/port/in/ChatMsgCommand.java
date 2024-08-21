package com.freediving.communityservice.application.port.in;

import com.freediving.common.SelfValidating;
import com.freediving.communityservice.adapter.out.persistence.constant.MsgType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class ChatMsgCommand extends SelfValidating<ChatMsgCommand> {

	@NotNull
	private Long chatRoomId;

	@NotBlank
	private String msg;

	@NotNull
	private MsgType msgType;

	private Long replyToMsgId;

	@NotNull
	private Long createdBy;

	public ChatMsgCommand(Long chatRoomId, String msg, MsgType msgType, Long replyToMsgId, Long createdBy) {
		this.chatRoomId = chatRoomId;
		this.msg = msg;
		this.msgType = msgType;
		this.replyToMsgId = replyToMsgId;
		this.createdBy = createdBy;
		this.validateSelf();
	}
}
