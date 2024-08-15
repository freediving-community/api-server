package com.freediving.communityservice.application.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import com.freediving.communityservice.adapter.out.persistence.constant.ChatType;
import com.freediving.communityservice.adapter.out.persistence.constant.MsgType;
import com.freediving.communityservice.application.port.in.BuddyChatRoomCommand;
import com.freediving.communityservice.application.port.in.ChatRoomCommand;
import com.freediving.communityservice.application.port.in.ChatUseCase;
import com.freediving.communityservice.application.port.out.ChatMsgCreationPort;
import com.freediving.communityservice.application.port.out.ChatMsgReadPort;
import com.freediving.communityservice.application.port.out.ChatRoomCreationPort;
import com.freediving.communityservice.application.port.out.ChatRoomEditPort;
import com.freediving.communityservice.application.port.out.ChatRoomReadPort;
import com.freediving.communityservice.application.port.out.ChatRoomUserCreationPort;
import com.freediving.communityservice.application.port.out.ChatRoomUserDeletePort;
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
	private final ChatRoomEditPort chatRoomEditPort;

	private final ChatRoomUserReadPort chatRoomUserReadPort;
	private final ChatRoomUserCreationPort chatRoomUserCreationPort;
	private final ChatRoomUserDeletePort chatRoomUserDeletePort;

	private final ChatMsgCreationPort chatMsgCreationPort;
	private final ChatMsgReadPort chatMsgReadPort;

	private final MemberFeignPort memberFeignPort;
	private final CommunityMessage commMessage;

	@Override
	public ChatRoomResponse handleBuddyChatRoom(BuddyChatRoomCommand command) {

		ChatRoom chatRoom = chatRoomReadPort.getChatRoom(command.getChatType(), command.getEventId());
		UserInfo buddyEventCreateUser = memberFeignPort.getUserInfoByUserId(command.getCreatedBy(), false);
		if (chatRoom == null) {
			return createNewChatRoom(
				command.getChatType(),
				command.getEventId(),
				buddyEventCreateUser,
				makeBuddyChatRoomTitle(
					buddyEventCreateUser.getNickname(),
					command.getEventStartDate(),
					command.getDivingPools()
				),
				"" // TODO: 클릭 시 버디API로 요청으로 변경 시 컬럼 삭제 예정
			);
		}
		// TODO: Buddy쪽의 Status가 common으로 나오면 대체
		if (!command.getStatus().equals("모집 삭제")) {
			List<ChatRoomUser> users = chatRoomUserReadPort.getAllUserByRoomId(chatRoom.getChatRoomId());
			// users.stream().allMatch(chatRoomUser -> )

			chatRoomEditPort.editChatRoom(
				buddyEventCreateUser.getUserId(),
				command.getChatType(),
				command.getEventId(),
				makeBuddyChatRoomTitle(
					buddyEventCreateUser.getNickname(),
					command.getEventStartDate(),
					command.getDivingPools()
				),
				command.getParticipants().size() + 1L,
				""
			);
		}

		return null;
	}

	/*
	 * 버디 이벤트로부터 받은 채팅방 입장(없는 경우 생성 후 입장) 처리
	 * */
	@Override
	public ChatRoomResponse requestChatRoom(ChatRoomCommand command) {

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
			// return createNewChatRoom(command, buddyEventCreateUser, buddyEventInfoTitle, buddyEventOpenChatRoomURL);
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

	private ChatRoomResponse createNewChatRoom(ChatType chatType, Long targetId, UserInfo requestUser,
		String title, String openChatRoomURL) {
		ChatRoom chatRoom = chatRoomCreationPort.createChatRoom(
			requestUser.getUserId(),
			chatType,
			targetId,
			title,
			1L,
			openChatRoomURL
		);

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
				.title(title)
				.openChatRoomURL(openChatRoomURL)
				.createdBy(requestUser.getUserId())
				.build()
			)
			.hasNewMsg(false)
			.build();
	}

	private String makeBuddyChatRoomTitle(String nickname, LocalDateTime eventStartDate, List<String> divingPools) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 d일 HH시");
		StringBuffer sb = new StringBuffer();
		sb.append(nickname);
		sb.append("의 ");
		sb.append(eventStartDate.format(formatter));
		sb.append(" ");
		sb.append(divingPools.getFirst());
		sb.append(" 모임");
		return sb.toString();
	}
}
