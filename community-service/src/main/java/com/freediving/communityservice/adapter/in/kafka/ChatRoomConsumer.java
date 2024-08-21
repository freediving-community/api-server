package com.freediving.communityservice.adapter.in.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freediving.common.dto.kafka.BuddyEventInfoDTO;
import com.freediving.communityservice.adapter.out.dto.chat.ChatRoomResponse;
import com.freediving.communityservice.adapter.out.persistence.constant.ChatType;
import com.freediving.communityservice.application.port.in.BuddyChatRoomCommand;
import com.freediving.communityservice.application.port.in.ChatRoomEventUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatRoomConsumer {

	private final ObjectMapper objectMapper;
	private final ChatRoomEventUseCase chatRoomEventUseCase;

	@KafkaListener(topics = "${task.topic.buddyservice.buddy-event-users}"/*, groupId = "${spring.kafka.consumer.group-id}"*/)
	public void consumeBuddyEventUsers(ConsumerRecord<String, String> consumerRecord) {
		String jsonValue = consumerRecord.value();
		BuddyEventInfoDTO eventInfoDTO = null;
		log.info("consumeBuddyEventUsers : {}", jsonValue);

		try {
			eventInfoDTO = objectMapper.readValue(jsonValue, BuddyEventInfoDTO.class);
		} catch (Exception e) {
			log.error("consumeBuddyEventUsers >>> ", e);
		}

		ChatRoomResponse chatRoom = chatRoomEventUseCase.handleBuddyChatRoom(
			BuddyChatRoomCommand.builder()
				.eventId(eventInfoDTO.getEventId())
				.createdBy(eventInfoDTO.getCreatedBy())
				.eventStartDate(eventInfoDTO.getEventStartDate())
				.participants(eventInfoDTO.getParticipants())
				.status(eventInfoDTO.getStatus())
				.divingPools(eventInfoDTO.getDivingPools())
				.openChatRoomUrl(eventInfoDTO.getOpenChatRoomUrl())
				.chatType(ChatType.BUDDY)
				.build()
		);
	}

}
