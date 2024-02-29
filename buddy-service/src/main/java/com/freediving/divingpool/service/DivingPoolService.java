package com.freediving.divingpool.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.divingpool.data.dao.DivingPoolJpaEntity;
import com.freediving.divingpool.data.dto.DivingPoolListResponse;
import com.freediving.divingpool.data.dto.DivingPoolResponse;
import com.freediving.divingpool.repository.DivingPoolRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DivingPoolService {

	private final DivingPoolRepository divingPoolRepository;

	/**
	 * 노출 중인 모든 다이빙 풀을 조회한다.
	 *
	 * @return DivingPool DTO 모델
	 */

	public DivingPoolListResponse findByAllDivingPool() throws BuddyMeException {

		List<DivingPoolJpaEntity> divingPoolsJpaEntity = divingPoolRepository.findAllByIsVisibleTrue();

		// No Content
		if (divingPoolsJpaEntity == null || divingPoolsJpaEntity.size() == 0)
			throw new BuddyMeException(ServiceStatusCode.NO_CONTENT);

		List<DivingPoolResponse> divingPoolResponseList = divingPoolsJpaEntity.stream().map(e -> {
				return DivingPoolResponse.of(e);
			})
			.collect(Collectors.toList());

		return DivingPoolListResponse.builder().divingPools(divingPoolResponseList).build();

	}

}
