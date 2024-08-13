package com.freediving.communityservice.adapter.out.persistence.chat;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.communityservice.adapter.out.dto.chat.ChatMsgResponse;
import com.freediving.communityservice.adapter.out.persistence.constant.MsgType;
import com.freediving.communityservice.application.port.out.ChatMsgCreationPort;
import com.freediving.communityservice.application.port.out.ChatMsgReadPort;
import com.freediving.communityservice.config.CommunityMessage;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ChatMsgPersistenceAdapter implements ChatMsgCreationPort, ChatMsgReadPort {

	private final ChatMsgRepository chatMsgRepository;
	private final CommunityMessage communityMessage;

	@Value("${community.api.chat.msg.paging}")
	private String CHAT_MSG_PAGING_SIZE;

	@Override
	public ChatMsgJpaEntity saveMsg(Long chatRoomId, String msg, MsgType msgType, Long replyToMsgId, Long createdBy) {
		return chatMsgRepository.save(
			ChatMsgJpaEntity.builder()
				.chatRoomId(chatRoomId)
				.msg(msg)
				.msgType(msgType)
				.replyToMsgId(replyToMsgId)
				.createdBy(createdBy)
				.build()
		);
	}

	@Override
	public List<ChatMsgResponse> getRecentMsg(Long chatRoomId) {
		Optional<List<ChatMsgJpaEntity>> chatMsgJpaEntities = chatMsgRepository.findByChatRoomIdOrderByMsgIdDesc(
			chatRoomId, PageRequest.of(0, Integer.parseInt(CHAT_MSG_PAGING_SIZE)));
		return chatMsgJpaEntities.orElse(List.of()).stream().map(ChatMsgResponse::from).toList();
	}

	@Override
	public List<ChatMsgResponse> getMsgAfterId(Long chatRoomId, Long lastMsgId) {
		Optional<List<ChatMsgJpaEntity>> chatMsgJpaEntities = chatMsgRepository.findByChatRoomIdAndMsgIdLessThanOrderByMsgIdDesc(
			chatRoomId, lastMsgId,
			PageRequest.of(0, Integer.parseInt(CHAT_MSG_PAGING_SIZE)));

		return chatMsgJpaEntities.orElse(List.of()).stream().map(ChatMsgResponse::from).toList();
	}
}
