package com.freediving.divingpool.common;

import com.freediving.divingpool.data.dao.DivingPoolJpaEntity;

public class DivingPoolTestCommon {

	/**
	 * 테스트 용으로 다이빙풀 정보 given을 해주어야 하기에 자주 사용하기 좋은 함수화 시킴.
	 * @param divingPoolId 다이빙 풀 식별 ID
	 * @param divingPoolName 다이빙 풀 이름
	 * @param address 다이빙 풀 주소
	 * @param description 다이빙 풀 설명
	 * @param isVisible 노출여부 (true/false)
	 * @param displayOrder 노출 순번 ( 0~N)
	 * @return DivingPoolJpaEntity 객체
	 */
	public static DivingPoolJpaEntity generateDivingPoolJpaEntity(String divingPoolId, String divingPoolName,
		String address
		, String description, Boolean isVisible, Integer displayOrder) {

		return DivingPoolJpaEntity.builder()
			.divingPoolId(divingPoolId)
			.divingPoolName(divingPoolName)
			.address(address)
			.description(description)
			.isVisible(isVisible)
			.displayOrder(displayOrder).build();
	}

}
