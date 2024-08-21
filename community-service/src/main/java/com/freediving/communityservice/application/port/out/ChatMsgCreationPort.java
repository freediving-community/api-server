package com.freediving.communityservice.application.port.out;

import com.freediving.communityservice.adapter.out.persistence.chat.ChatMsgJpaEntity;
import com.freediving.communityservice.adapter.out.persistence.constant.MsgType;

public interface ChatMsgCreationPort {
	ChatMsgJpaEntity saveMsg(Long chatRoomId, String msg, MsgType msgType, Long replyToMsgId, Long createdBy);
}
