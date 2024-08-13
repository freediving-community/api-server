package com.freediving.communityservice.application.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.freediving.communityservice.adapter.out.dto.chat.ChatMessageLasChecked;
import com.freediving.communityservice.adapter.out.dto.chat.ChatMsgResponse;
import com.freediving.communityservice.adapter.out.dto.chat.ChatRoomBuddyEventInfo;
import com.freediving.communityservice.adapter.out.dto.chat.ChatRoomInfoResponse;
import com.freediving.communityservice.adapter.out.dto.chat.ChatRoomResponse;
import com.freediving.communityservice.adapter.out.dto.user.UserInfo;
import com.freediving.communityservice.adapter.out.persistence.chat.ChatMsgJpaEntity;
import com.freediving.communityservice.adapter.out.persistence.chat.ChatRoomUserId;
import com.freediving.communityservice.adapter.out.persistence.constant.MsgType;
import com.freediving.communityservice.application.port.in.ChatRoomCommand;
import com.freediving.communityservice.application.port.in.ChatUseCase;
import com.freediving.communityservice.application.port.out.ChatMsgCreationPort;
import com.freediving.communityservice.application.port.out.ChatMsgReadPort;
import com.freediving.communityservice.application.port.out.ChatRoomCreationPort;
import com.freediving.communityservice.application.port.out.ChatRoomReadPort;
import com.freediving.communityservice.application.port.out.ChatRoomUserCreationPort;
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
	private final ChatRoomCreationPort chatRoomCreationPort;

	private final ChatRoomUserReadPort chatRoomUserReadPort;
	private final ChatRoomUserCreationPort chatRoomUserCreationPort;
	// private final ChatRoomDeletePort chatRoomDeletePort;
	private final ChatMsgCreationPort chatMsgCreationPort;
	private final ChatMsgReadPort chatMsgReadPort;
	private final MemberFeignPort memberFeignPort;

	private final CommunityMessage commMessage;

	/*
	 * 버디 이벤트로부터 받은 채팅방 입장(없는 경우 생성 후 입장) 처리
	 * */
	@Override
	public ChatRoomResponse requestBuddyChatRoom(ChatRoomCommand command) {

		//TODO: 요청자에 대한 Buddy 서비스 권한 및 유효성 검사
		// * 카프카에서 반드시 최초에 방장이 생성을 하거나, 최초 요청 시점에 방장이 누군지 정보를 조회해야 함.

		// users.getBuddyEventCreateUserId
		UserInfo buddyEventCreateUser = memberFeignPort.getUserInfoByUserId(
			command.getUserProvider().getRequestUserId(), false);

		// List<UserInfo> users = buddyFeignPort.getBuddyEventStatus(command.getTargetId())
		Map<Long, UserInfo> buddyEventUserMap = memberFeignPort.getUserMapByUserIds(List.of(1L, 2L, 3L), true);

		//TODO: Kafka -> BuddyEvent로부터 받아온 정보를 기반으로 생성 (Plan B. 입장 시점에 Buddy Internal API를 조회)
		String buddyEventInfoTitle = "보리보리의 파라다이브 24-08-10 11시 모임";
		String buddyEventOpenChatRoomURL = "https://open.kakao.com/o/sHRwpFCg";

		ChatRoom chatRoom = chatRoomReadPort.getChatRoom(command.getChatType(), command.getTargetId());
		if (chatRoom == null) {
			// 최초 생성, 생성자가 방장.
			return createNewChatRoom(command, buddyEventCreateUser, buddyEventInfoTitle, buddyEventOpenChatRoomURL);
		}

		chatRoom.isEnabled(); // 비활성화 시 Type별 수행
		//TODO: handleDisabledChatRoom( chatRoom);

		List<ChatRoomUser> chatRoomUsers = chatRoomUserReadPort.getAllUserByRoomId(chatRoom.getChatRoomId());
		//TODO: buddyEventCreateUser는 승인자 목록, participantIds 에 없는 경우는 입장하지 않은 사람임.
		List<ChatMessageLasChecked> chatMessageLasCheckeds =
			chatRoomUsers
				.stream()
				.map(chatRoomUser -> new ChatMessageLasChecked(
					chatRoomUser.getUserId(),
					chatRoomUser.getLastCheckedMsgId()
				)).toList();

		List<ChatMsgResponse> msgList = chatMsgReadPort.getRecentMsg(chatRoom.getChatRoomId());

		return ChatRoomResponse.builder()
			.roomInfo(ChatRoomInfoResponse.from(chatRoom))
			.users((List<UserInfo>)buddyEventUserMap.values())
			.messages(msgList)
			.buddyEventInfo(ChatRoomBuddyEventInfo.builder()
				.buddyEventId(1L)
				.title(buddyEventInfoTitle)
				.openChatRoomURL(buddyEventOpenChatRoomURL)
				.createdBy(buddyEventCreateUser.getUserId())
				.build()
			)
			.hasNewMsg(false)
			.build();

	}

	private ChatRoomResponse createNewChatRoom(ChatRoomCommand command, UserInfo requestUser,
		String buddyEventInfoTitle, String buddyEventOpenChatRoomURL) {
		ChatRoom chatRoom;
		chatRoom = chatRoomCreationPort.createChatRoom(
			// TODO: 버디 이벤트 방장 사용자 정보
			requestUser.getUserId(),
			command.getChatType(),
			command.getTargetId(),
			buddyEventInfoTitle,
			1L,
			buddyEventOpenChatRoomURL);

		ChatMsgJpaEntity savedMsg = chatMsgCreationPort.saveMsg(
			chatRoom.getChatRoomId(),
			commMessage.getMessage("chat.system.room.enter", requestUser.getNickname()),
			MsgType.SYSTEM,
			null,
			requestUser.getUserId()
		);

		chatRoomUserCreationPort.enterChatRoom(
			ChatRoomUserId.builder()
				.chatRoomId(chatRoom.getChatRoomId())
				.userId(requestUser.getUserId())
				.build(),
			savedMsg.getMsgId()
		);

		return ChatRoomResponse.builder()
			.roomInfo(ChatRoomInfoResponse.from(chatRoom))
			.users(List.of(requestUser))
			.messages(List.of(ChatMsgResponse.from(savedMsg)))
			.buddyEventInfo(ChatRoomBuddyEventInfo.builder()
				.buddyEventId(1L)
				.title(buddyEventInfoTitle)
				.openChatRoomURL(buddyEventOpenChatRoomURL)
				.createdBy(requestUser.getUserId())
				.build()
			)
			.hasNewMsg(false)
			.build();
	}
}
