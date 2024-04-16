package com.freediving.divingpool.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.freediving.common.enumerate.DivingPool;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.divingpool.common.DivingPoolTestCommon;
import com.freediving.divingpool.data.dto.DivingPoolListResponse;
import com.freediving.divingpool.repository.DivingPoolRepository;

@SpringBootTest
@ActiveProfiles("local")
class DivingPoolServiceTest {

	@Autowired
	private DivingPoolRepository divingPoolRepository;

	@Autowired
	private DivingPoolService divingPoolService;

	@Test
	@DisplayName("노출 중인 다이빙 풀을 조회하나 한 건도 없다면 204 BuddyMeException 발생한다.")
	void findByAllDivingPoolThrowByBuddyMeException() {

		// schema.sql, data.sql로 등록된 다이빙 풀 정보가 존재할 수도 있으니 삭제 처리 해버린다.
		divingPoolRepository.deleteAll();

		// 1. given = 다이빙 풀 노출 여부 false만 존재하게 생성한다..
		divingPoolRepository.save(DivingPoolTestCommon.generateDivingPoolJpaEntity("PARADIVE", "파라다이브"
			, "경기도 시흥시", "파라다이브 설명", false, 1));

		// 2.when, then
		assertThatThrownBy(() -> divingPoolService.findByAllDivingPool())
			.isInstanceOf(BuddyMeException.class).extracting("serviceStatusCode")
			.isEqualTo(ServiceStatusCode.NO_CONTENT);

	}

	@Test
	@DisplayName("노출 중인 다이빙 풀을 조회하고 DTO 객체로 변환하여 응답한다.")
	void findByAllDivingPool() {
		// schema.sql, data.sql로 등록된 다이빙 풀 정보가 존재할 수도 있으니 삭제 처리 해버린다.
		divingPoolRepository.deleteAll();

		// 1. given = 다이빙 풀 정보를 2개 생성해준다. 하는 노출 중지 상태
		divingPoolRepository.save(DivingPoolTestCommon.generateDivingPoolJpaEntity("DEEPSTATION", "딥스테이션"
			, "경기도 용인시 처인구 포곡읍", "딥스테이션 설명", true, 0));
		divingPoolRepository.save(DivingPoolTestCommon.generateDivingPoolJpaEntity("PARADIVE", "파라다이브"
			, "경기도 시흥시", "파라다이브 설명", false, 1));

		// 2.when
		DivingPoolListResponse divingPoolListResponse = divingPoolService.findByAllDivingPool();

		// 3. then
		assertThat(divingPoolListResponse).isNotNull();
		assertThat(divingPoolListResponse.getDivingPools()).hasSize(1)
			.extracting("divingPoolId", "divingPoolName", "address", "description", "displayOrder")
			.containsExactlyInAnyOrder(tuple(DivingPool.DEEPSTATION, "딥스테이션"
				, "경기도 용인시 처인구 포곡읍", "딥스테이션 설명", 0));

	}
}