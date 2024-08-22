package com.freediving.communityservice.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.communityservice.adapter.out.dto.chat.ChatMessageLastChecked;
import com.freediving.communityservice.adapter.out.dto.chat.ChatMsgResponse;
import com.freediving.communityservice.adapter.out.dto.chat.ChatRoomBuddyEventInfo;
import com.freediving.communityservice.adapter.out.dto.chat.ChatRoomInfoResponse;
import com.freediving.communityservice.adapter.out.dto.chat.ChatRoomListResponse;
import com.freediving.communityservice.adapter.out.dto.chat.ChatRoomResponse;
import com.freediving.communityservice.adapter.out.dto.user.UserInfo;
import com.freediving.communityservice.application.port.in.ChatMsgCommand;
import com.freediving.communityservice.application.port.in.ChatRoomCommand;
import com.freediving.communityservice.application.port.in.ChatRoomListQueryCommand;
import com.freediving.communityservice.application.port.in.ChatUseCase;
import com.freediving.communityservice.application.port.out.ChatMsgCreationPort;
import com.freediving.communityservice.application.port.out.ChatMsgReadPort;
import com.freediving.communityservice.application.port.out.ChatRoomReadPort;
import com.freediving.communityservice.application.port.out.ChatRoomUserReadPort;
import com.freediving.communityservice.application.port.out.external.MemberFeignPort;
import com.freediving.communityservice.config.CommunityMessage;
import com.freediving.communityservice.domain.ChatRoom;
import com.freediving.communityservice.domain.ChatRoomUser;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ChatService implements ChatUseCase {

	private final ChatRoomReadPort chatRoomReadPort;

	private final ChatRoomUserReadPort chatRoomUserReadPort;

	private final ChatMsgCreationPort chatMsgCreationPort;
	private final ChatMsgReadPort chatMsgReadPort;

	private final MemberFeignPort memberFeignPort;
	private final CommunityMessage commMessage;

	/*
	 * 버디 이벤트 채팅방 입장
	 * */
	@Override
	public ChatRoomResponse enterChatRoom(ChatRoomCommand command) {

		ChatRoom chatRoom = chatRoomReadPort.getChatRoom(command.getChatType(), command.getTargetId());
		if (ObjectUtils.isEmpty(chatRoom)) {
			throw new BuddyMeException(ServiceStatusCode.COMMUNITY_SERVICE, "해당 채팅방이 없습니다.");
		}

		List<ChatRoomUser> chatRoomUsers = chatRoomUserReadPort.getAllUserByRoomId(chatRoom.getChatRoomId());
		List<Long> joinedUserIds = chatRoomUsers.stream().map(ChatRoomUser::getUserId).toList();

		validateChatRoom(command.getUserProvider().getRequestUserId(), joinedUserIds, chatRoom);

		Map<Long, UserInfo> allUserMap = memberFeignPort.getUserMapByUserIds(joinedUserIds, true);

		List<ChatMessageLastChecked> chatMessageLastCheckeds =
			chatRoomUsers
				.stream()
				.map(chatRoomUser -> new ChatMessageLastChecked(
					chatRoomUser.getUserId(),
					chatRoomUser.getLastCheckedMsgId()
				)).toList();

		List<ChatMsgResponse> msgList = chatMsgReadPort.getRecentMsg(chatRoom.getChatRoomId());

		return ChatRoomResponse.builder()
			.roomInfo(ChatRoomInfoResponse.from(chatRoom))
			.users(new ArrayList<>(allUserMap.values()))
			.messages(msgList)
			.buddyEventInfo(ChatRoomBuddyEventInfo.builder()
				.buddyEventId(chatRoom.getTargetId())
				.title(chatRoom.getTitle())
				.openChatRoomURL(chatRoom.getOpenChatRoomURL())
				.createdBy(chatRoom.getCreatedBy())
				.build()
			)
			.chatMessageLastCheckedList(chatMessageLastCheckeds)
			.build();
	}

	@Override
	public ChatMsgResponse newChatMsg(ChatMsgCommand command) {
		if (!ObjectUtils.isEmpty(command.getReplyToMsgId())) {
			//TODO: 해당 채팅방에 존재하는 메세지인지 확인.
		}
		return ChatMsgResponse.from(chatMsgCreationPort.saveMsg(
				command.getChatRoomId(),
				command.getMsg(),
				command.getMsgType(),
				command.getReplyToMsgId(),
				command.getCreatedBy()
			)
		);
	}

	@Override
	public List<ChatRoomListResponse> getChatRoomList(ChatRoomListQueryCommand command) {
		return chatRoomReadPort.getChatRoomList(
			command.getUserProvider().getRequestUserId(),
			command.getChatType());
	}

	private void validateChatRoom(Long requestUserId, List<Long> joinedUserIds, ChatRoom chatRoom) {
		if (!joinedUserIds.contains(requestUserId)) {
			throw new BuddyMeException(ServiceStatusCode.COMMUNITY_SERVICE, "해당 채팅방이 없습니다.");
		}

		if (!ObjectUtils.isEmpty(chatRoom.getDeletedAt())) {
			throw new BuddyMeException(ServiceStatusCode.COMMUNITY_SERVICE, "해당 채팅방이 없습니다.");
		}

		if (!chatRoom.isEnabled()) {
			//TODO: handleDisabledChatRoom( chatRoom);
			throw new BuddyMeException(ServiceStatusCode.COMMUNITY_SERVICE, "해당 채팅방이 없습니다.");
		}
	}

}
