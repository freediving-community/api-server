package com.freediving.notiservice.adapter.in.kafka;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freediving.notiservice.adapter.in.kafka.dto.CreateNotiRequest;
import com.freediving.notiservice.application.port.in.CreateNotiCommand;
import com.freediving.notiservice.application.port.in.CreateNotiUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotiConsumer {

	private final ObjectMapper objectMapper;
	private final CreateNotiUseCase createNotiUseCase;

	@KafkaListener(topics = "${task.topic.common-noti}", groupId = "${spring.kafka.consumer.group-id}")
	public void consumeNoti(String message) {
		CreateNotiRequest req = null;
		try {
			req = objectMapper.readValue(message, CreateNotiRequest.class);
			log.info("CREATRE NOTI REQUEST : {}", req);
		} catch (Exception e) {
			log.error("CREATE NOTI REQUEST ERROR WHILE JSON PARSING : ", e);
		}

		if (!ObjectUtils.isEmpty(req)) {
			CreateNotiCommand command = CreateNotiCommand.builder()
				.targetUserId(req.getTargetUserId())
				.screenName(req.getScreenName())
				.type(req.getType())
				.sourceUserId(req.getSourceUserId())
				.title(req.getTitle())
				.content(req.getContent())
				.linkCode(req.getLinkCode())
				.linkData(req.getLinkData())
				.createdAt(req.getCreatedAt())
				.build();
			createNotiUseCase.createNoti(command);
		}
	}
}
