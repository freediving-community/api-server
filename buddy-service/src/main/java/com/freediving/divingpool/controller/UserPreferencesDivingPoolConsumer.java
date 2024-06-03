package com.freediving.divingpool.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freediving.common.enumerate.DivingPool;
import com.freediving.divingpool.data.dao.persistence.UserDivingPoolEntity;
import com.freediving.divingpool.data.dto.request.PoolPreference;
import com.freediving.divingpool.repository.UserDivingPoolRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserPreferencesDivingPoolConsumer {

	private final UserDivingPoolRepository poolRepository;
	private final ObjectMapper mapper;

	@Value("${task.topic.user-pool-preferences}")
	private String poolTopic;

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

	private DivingPool mapToDivingPool(String pool) {
		try {
			return DivingPool.valueOf(pool);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

}
