package com.freediving.buddyservice.adapter.in.kafka;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freediving.buddyservice.adapter.in.kafka.dto.ConceptPreference;
import com.freediving.buddyservice.adapter.out.persistence.preference.UserBuddyEventConceptEntity;
import com.freediving.buddyservice.adapter.out.persistence.preference.UserBuddyEventConceptRepository;
import com.freediving.buddyservice.domain.enumeration.BuddyEventConcept;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserPreferencesConceptConsumer {

	private final UserBuddyEventConceptRepository conceptRepository;

	private final ObjectMapper mapper;

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

	private BuddyEventConcept mapToBuddyEventConcept(String concept) {
		try {
			return BuddyEventConcept.valueOf(concept);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}
