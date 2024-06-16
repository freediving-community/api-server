package com.freediving.memberservice.adapter.out.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.memberservice.adapter.out.dto.UserConceptRequest;
import com.freediving.memberservice.adapter.out.dto.UserPoolRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author         : sasca37
 * @Date           : 2024/05/25
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/05/25        sasca37       최초 생성
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class UserPreferencesProducer {

	@Value("${task.topic.user-pool-preferences}")
	private String poolTopic;

	@Value("${task.topic.user-concept-preferences}")
	private String conceptTopic;

	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper objectMapper;

	public void sendUserPool(UserPoolRequest userPoolRequest) {
		try {
			String msg = objectMapper.writeValueAsString(userPoolRequest);
			log.info("[Produce sendUserPool] userId : {}, msg : {}",userPoolRequest.getUserId(),  msg);
			this.kafkaTemplate.send(poolTopic, objectMapper.writeValueAsString(userPoolRequest));
		} catch (Exception e) {
			log.error("ERROR [Produce sendUserPool] userId : {}, error : ", userPoolRequest.getUserId(), e);
			throw new BuddyMeException(ServiceStatusCode.INTERVAL_SERVER_ERROR, "userPoolRequest 카프카 Producing 중 오류 발생");
		}
	}

	public void sendUserConcept(UserConceptRequest userConceptRequest) {
		try {
			String msg = objectMapper.writeValueAsString(userConceptRequest);
			log.info("[Produce sendUserConcept] userId : {}, msg : {}", userConceptRequest.getUserId(), msg);
			this.kafkaTemplate.send(conceptTopic, objectMapper.writeValueAsString(userConceptRequest));
		} catch (Exception e) {
			log.error("ERROR [Produce sendUserPool] userId : {}, error : ", userConceptRequest.getUserId(), e);
			throw new BuddyMeException(ServiceStatusCode.INTERVAL_SERVER_ERROR, "userConceptRequest 카프카 Producing 중 오류 발생");
		}
	}
}
