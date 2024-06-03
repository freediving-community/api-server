package com.freediving.divingpool.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.divingpool.config.enumerate.DetailLevel;
import com.freediving.divingpool.data.dao.DivingPoolJpaEntity;
import com.freediving.divingpool.data.dao.persistence.UserDivingPoolEntity;
import com.freediving.divingpool.data.dto.DivingPoolListResponse;
import com.freediving.divingpool.data.dto.DivingPoolResponse;
import com.freediving.divingpool.data.dto.DivingPoolSimpleResponse;
import com.freediving.divingpool.data.dto.UserDivingPoolListResponse;
import com.freediving.divingpool.repository.DivingPoolRepository;
import com.freediving.divingpool.repository.UserDivingPoolRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DivingPoolService {

	private final DivingPoolRepository divingPoolRepository;
	private final UserDivingPoolRepository userDivingPoolRepository;

	/**
	 * 노출 중인 모든 다이빙 풀을 조회한다.
	 *
	 * @return DivingPool DTO 모델
	 */

	public DivingPoolListResponse findByAllDivingPool(DetailLevel detail) throws BuddyMeException {

		List<DivingPoolJpaEntity> divingPoolsJpaEntity = divingPoolRepository.findAllByIsVisibleTrueOrderByDisplayOrderAsc();

		// No Content
		if (divingPoolsJpaEntity == null || divingPoolsJpaEntity.size() == 0)
			throw new BuddyMeException(ServiceStatusCode.NO_CONTENT);

		List<Object> divingPoolResponseList = divingPoolsJpaEntity.stream().map(e -> {
				if (detail.equals(DetailLevel.HIGH))
					return DivingPoolResponse.of(e);
				else
					return DivingPoolSimpleResponse.of(e);
			})
			.collect(Collectors.toList());

		return DivingPoolListResponse.builder().divingPools(divingPoolResponseList).build();

	}

	public UserDivingPoolListResponse findByAllDivingPoolForInternal(Long userId) throws BuddyMeException {

		List<UserDivingPoolEntity> divingPoolsJpaEntity = userDivingPoolRepository.findAllByUserId(userId);

		// No Content
		if (divingPoolsJpaEntity == null || divingPoolsJpaEntity.size() == 0)
			throw new BuddyMeException(ServiceStatusCode.NO_CONTENT);

		UserDivingPoolListResponse result = UserDivingPoolListResponse.builder().build();

		for (UserDivingPoolEntity entity : divingPoolsJpaEntity)
			result.add(entity.getDivingPoolId());

		return result;

	}

}
