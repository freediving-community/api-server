package com.freediving.communityservice.application.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
import com.freediving.communityservice.application.port.in.ChatRoomEventUseCase;
import com.freediving.communityservice.application.port.out.ChatMsgCreationPort;
import com.freediving.communityservice.application.port.out.ChatRoomCreationPort;
import com.freediving.communityservice.application.port.out.ChatRoomEditPort;
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
public class ChatEventConsumeService implements ChatRoomEventUseCase {

	private final ChatRoomReadPort chatRoomReadPort;
	private final ChatRoomCreationPort chatRoomCreationPort;
	private final ChatRoomEditPort chatRoomEditPort;

	private final ChatRoomUserReadPort chatRoomUserReadPort;
	private final ChatRoomUserCreationPort chatRoomUserCreationPort;
	// private final ChatRoomUserDeletePort chatRoomUserDeletePort;

	private final ChatMsgCreationPort chatMsgCreationPort;

	private final MemberFeignPort memberFeignPort;
	private final CommunityMessage commMessage;

	@Override
	public ChatRoomResponse handleBuddyChatRoom(BuddyChatRoomCommand command) {

		ChatRoom chatRoom = chatRoomReadPort.getChatRoom(command.getChatType(), command.getEventId());
		UserInfo buddyEventCreateUser = memberFeignPort.getUserInfoByUserId(command.getCreatedBy(), false);

		if (ObjectUtils.isEmpty(chatRoom)) {
			return createNewChatRoom(
				command.getChatType(),
				command.getEventId(),
				buddyEventCreateUser,
				makeBuddyChatRoomTitle(
					buddyEventCreateUser.getNickname(),
					command.getEventStartDate(),
					command.getDivingPools()
				),
				command.getOpenChatRoomUrl()
			);
		}

		// TODO: BuddyEventStatus가 common으로 나오면 대체
		// 현재 채팅방 변경사항 반영
		if (Arrays.asList("모집 중", "모집 마감").contains(command.getStatus())) {
			// 채팅방 사용자 입장,퇴장 메시지 처리
			List<ChatRoomUser> oldUsers = chatRoomUserReadPort.getAllUserByRoomId(chatRoom.getChatRoomId());
			List<Long> oldUserIds = oldUsers.stream().map(ChatRoomUser::getUserId).toList();
			List<Long> nowUserIds = command.getParticipants();

			List<Long> newJoinUserIds = nowUserIds.stream().filter(u -> !oldUserIds.contains(u)).toList();
			List<Long> outUserIds = oldUserIds.stream().filter(u -> !nowUserIds.contains(u)).toList();

			chatRoomEditPort.editChatRoom(
				buddyEventCreateUser.getUserId(),
				command.getChatType(),
				command.getEventId(),
				makeBuddyChatRoomTitle(
					buddyEventCreateUser.getNickname(),
					command.getEventStartDate(),
					command.getDivingPools()
				),
				(long)command.getParticipants().size(),
				command.getOpenChatRoomUrl()
			);

			if (!newJoinUserIds.isEmpty()) {
				Map<Long, UserInfo> newUsers = memberFeignPort.getUserMapByUserIds(newJoinUserIds, false);
				for (Long newJoinUser : newJoinUserIds) {
					ChatMsgJpaEntity savedMsg = chatMsgCreationPort.saveMsg(
						chatRoom.getChatRoomId(),
						commMessage.getMessage("chat.system.room.enter", newUsers.get(newJoinUser).getNickname()),
						MsgType.SYSTEM,
						null,
						newJoinUser
					);

					chatRoomUserCreationPort.enterChatRoom(
						ChatRoomUserId.builder()
							.chatRoomId(chatRoom.getChatRoomId())
							.userId(newJoinUser)
							.build(),
						savedMsg.getMsgId()
					);
				}
			}

			if (!outUserIds.isEmpty()) {
				Map<Long, UserInfo> outUsers = memberFeignPort.getUserMapByUserIds(outUserIds, false);
				for (Long outUser : outUserIds) {
					ChatMsgJpaEntity savedMsg = chatMsgCreationPort.saveMsg(
						chatRoom.getChatRoomId(),
						commMessage.getMessage("chat.system.room.out", outUsers.get(outUser).getNickname()),
						MsgType.SYSTEM,
						null,
						outUser
					);

					chatRoomUserCreationPort.leaveChatRoom(
						ChatRoomUserId.builder()
							.chatRoomId(chatRoom.getChatRoomId())
							.userId(outUser)
							.build()
					);
				}
			}

		} else if ("모집 삭제".equals(command.getStatus())) {

		}

		return null;
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
