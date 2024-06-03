package com.freediving.buddyservice.adapter.out.persistence.event.divingpool;

import java.util.List;
import java.util.Map;

import com.freediving.buddyservice.adapter.out.persistence.event.divingpool.querydsl.BuddyEventDivingPoolMappingRepoDSL;
import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.BuddyEventDivingPoolMappingProjectDto;
import com.freediving.buddyservice.application.port.out.web.query.BuddyEventDivingPoolMappingPort;
import com.freediving.common.config.annotation.PersistenceAdapter;

import lombok.RequiredArgsConstructor;

/**
 * Persistence 와의 연결을 담당하는 Adapter
 * 이 객체는 Persistence 와의 명령 수행만 담당한다. 비즈니스 적인 로직은 불가.
 *
 * @author 준희조
 * @version 1.0.0
 * 작성일 2023-12-27
 **/
@RequiredArgsConstructor
@PersistenceAdapter
public class BuddyEventDivingPoolMappingPersistenceAdapter implements BuddyEventDivingPoolMappingPort {

	private final BuddyEventDivingPoolMappingRepoDSL buddyEventDivingPoolMappingRepoDSL;

	public Map<Long, List<BuddyEventDivingPoolMappingProjectDto>> getAllDivingPoolMapping(List<Long> ids) {
		// 1.해당 이벤트 다이빙 풀 조회하기

		Map<Long, List<BuddyEventDivingPoolMappingProjectDto>> allDivingPoolMappingByEventId = buddyEventDivingPoolMappingRepoDSL.findDivingPoolMappingAllByEventIds(
			ids);
		return allDivingPoolMappingByEventId;
	}
}
