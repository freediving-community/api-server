package com.freediving.buddyservice.adapter.in.kafka;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freediving.buddyservice.adapter.in.kafka.dto.ConceptPreference;
import com.freediving.buddyservice.adapter.in.kafka.dto.PoolPreference;
import com.freediving.buddyservice.adapter.out.persistence.preference.UserBuddyEventConceptEntity;
import com.freediving.buddyservice.adapter.out.persistence.preference.UserBuddyEventConceptRepository;
import com.freediving.buddyservice.adapter.out.persistence.preference.UserDivingPoolEntity;
import com.freediving.buddyservice.adapter.out.persistence.preference.UserDivingPoolRepository;
import com.freediving.buddyservice.domain.enumeration.BuddyEventConcept;
import com.freediving.common.enumerate.DivingPool;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserPreferencesConsumer {

	private final UserDivingPoolRepository poolRepository;
	private final UserBuddyEventConceptRepository conceptRepository;

	private final ObjectMapper mapper;

	@Value("${task.topic.user-pool-preferences}")
	private String poolTopic;

	@Value("${task.topic.user-concept-preferences}")
	private String conceptTopic;

	@KafkaListener(topics = "${task.topic.user-pool-preferences}", groupId = "${spring.kafka.consumer.group-id}")
	@Transactional
	public void handlePoolPreferences(String message) {
		try {
			PoolPreference poolPreference = mapper.readValue(message, PoolPreference.class);
			Long userId = poolPreference.getUserId();
			Set<String> preferredPools = poolPreference.getPreferredPools();

			// 유효한 DivingPool로 매핑
			Set<DivingPool> validPools = preferredPools.stream()
				.map(this::mapToDivingPool)
				.filter(pool -> pool != null)
				.limit(2)
				.collect(Collectors.toSet());

			// 기존 다이빙 풀 선호 삭제
			poolRepository.deleteByUserId(userId);

			// 새로운 다이빙 풀 선호 저장
			for (DivingPool poolId : validPools) {
				UserDivingPoolEntity entity = new UserDivingPoolEntity(userId, poolId);
				poolRepository.save(entity);
			}

			System.out.println("Updated pool preference: " + userId + ", " + validPools);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@KafkaListener(topics = "${task.topic.user-concept-preferences}", groupId = "${spring.kafka.consumer.group-id}")
	@Transactional
	public void handleConceptPreferences(String message) {
		try {
			ConceptPreference conceptPreference = mapper.readValue(message, ConceptPreference.class);
			Long userId = conceptPreference.getUserId();
			Set<String> preferredConcepts = conceptPreference.getPreferredConcepts();

			// 유효한 BuddyEventConcept로 매핑
			Set<BuddyEventConcept> validConcepts = preferredConcepts.stream()
				.map(this::mapToBuddyEventConcept)
				.filter(concept -> concept != null)
				.limit(2)
				.collect(Collectors.toSet());

			// 기존 다이빙 컨셉 선호 삭제
			conceptRepository.deleteByUserId(userId);

			// 새로운 다이빙 컨셉 선호 저장
			for (BuddyEventConcept concept : validConcepts) {
				UserBuddyEventConceptEntity entity = new UserBuddyEventConceptEntity(userId, concept);
				conceptRepository.save(entity);
			}

			System.out.println("Updated concept preference: " + userId + ", " + validConcepts);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	private DivingPool mapToDivingPool(String pool) {
		try {
			return DivingPool.valueOf(pool);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	private BuddyEventConcept mapToBuddyEventConcept(String concept) {
		try {
			return BuddyEventConcept.valueOf(concept);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}
