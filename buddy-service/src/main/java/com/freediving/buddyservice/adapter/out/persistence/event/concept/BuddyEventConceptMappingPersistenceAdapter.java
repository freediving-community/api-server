package com.freediving.buddyservice.adapter.out.persistence.event.concept;

import java.util.List;
import java.util.Map;

import com.freediving.buddyservice.adapter.out.persistence.event.concept.querydsl.BuddyEventConceptMappingRepoDSL;
import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.BuddyEventConceptMappingProjectDto;
import com.freediving.buddyservice.application.port.out.web.query.BuddyEventConceptMappingPort;
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
public class BuddyEventConceptMappingPersistenceAdapter implements BuddyEventConceptMappingPort {

	private final BuddyEventConceptMappingRepoDSL buddyEventConceptMappingRepoDSL;

	public Map<Long, List<BuddyEventConceptMappingProjectDto>> getAllConceptMapping(List<Long> ids) {
		// 2.해당 이벤트 컨셉 조회하기
		Map<Long, List<BuddyEventConceptMappingProjectDto>> allConceptMappingByEventId = buddyEventConceptMappingRepoDSL.findConceptMappingAllByEventIds(
			ids);
		return allConceptMappingByEventId;
	}
}
