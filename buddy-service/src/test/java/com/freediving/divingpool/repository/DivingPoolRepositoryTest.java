package com.freediving.divingpool.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import com.freediving.divingpool.common.DivingPoolTestCommon;
import com.freediving.divingpool.data.dao.DivingPoolJpaEntity;

@ActiveProfiles("local")
@DataJpaTest // JPA 관련된 Bean만 주입받아 테스트 가능.
@EnableJpaAuditing
class DivingPoolRepositoryTest {

	@Autowired
	private DivingPoolRepository divingPoolRepository;

	@AfterEach
	void tearDown() {
		divingPoolRepository.deleteAllInBatch();
	}

	@DisplayName("노출 중인 모든 다이빙 풀 정보를 모두 불러온다.")
	@Test
	void findAllByIsVisibleTrue() {

		// schema.sql, data.sql로 등록된 다이빙 풀 정보가 존재할 수도 있으니 삭제 처리 해버린다.
		divingPoolRepository.deleteAll();

		// 1. given = 다이빙 풀 정보를 2개 생성해준다.
		divingPoolRepository.save(DivingPoolTestCommon.generateDivingPoolJpaEntity("DEEPSTATION", "딥스테이션"
			, "경기도 용인시 처인구 포곡읍", "딥스테이션 설명", true, 0));
		divingPoolRepository.save(DivingPoolTestCommon.generateDivingPoolJpaEntity("PARADIVE", "파라다이브"
			, "경기도 시흥시", "파라다이브 설명", false, 1));

		// 2.when
		List<DivingPoolJpaEntity> divingPoolResponses = divingPoolRepository.findAllByIsVisibleTrue();

		// then
		assertThat(divingPoolResponses).hasSize(1)
			.extracting("divingPoolId", "divingPoolName", "address", "description", "isVisible", "displayOrder")
			.containsExactlyInAnyOrder(tuple("DEEPSTATION", "딥스테이션"
				, "경기도 용인시 처인구 포곡읍", "딥스테이션 설명", true, 0));

	}

}