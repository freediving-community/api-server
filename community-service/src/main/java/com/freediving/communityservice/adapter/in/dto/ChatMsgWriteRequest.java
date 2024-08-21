package com.freediving.communityservice.adapter.in.dto;

import com.freediving.communityservice.adapter.out.persistence.constant.MsgType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Schema(description = "채팅방 메세지 등록")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatMsgWriteRequest {

	@Schema(description = "채팅 메세지", example = "안녕하세요. 채팅 내용입니다.")
	private String msg;

	@Schema(description = "메세지 타입", example = "chat, notice", implementation = MsgType.class)
	private MsgType msgType;

	@Schema(description = "답장하는 메세지의 ID", example = "2")
	private Long replyToMsgId;
}
