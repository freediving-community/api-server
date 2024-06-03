package com.freediving.buddyservice.adapter.out.persistence.event.join;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.freediving.buddyservice.adapter.out.persistence.event.join.querydsl.BuddyEventJoinRepoDSL;
import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.listing.BuddyEventJoinMappingProjectDto;
import com.freediving.buddyservice.application.port.out.web.query.BuddyEventJoinPort;
import com.freediving.buddyservice.domain.enumeration.ParticipationStatus;
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
public class BuddyEventJoinPersistenceAdapter implements BuddyEventJoinPort {

	private final BuddyEventJoinRepoDSL buddyEventJoinRepoDSL;

	public Map<Long, List<BuddyEventJoinMappingProjectDto>> getAllJoinMapping(List<Long> ids) {
		// 3. 참여자 User 정보 조회하기
		Map<Long, List<BuddyEventJoinMappingProjectDto>> allJoinMappingByEventId = buddyEventJoinRepoDSL.findJoinMappingAllByEventIds(
			ids);
		return allJoinMappingByEventId;
	}

	@Override
	public List<Long> getParticipantsOfEvent(Long eventId) {
		Map<Long, List<BuddyEventJoinMappingProjectDto>> allJoinMappingByEventId = buddyEventJoinRepoDSL.findJoinMappingAllByEventIds(
			List.of(eventId));

		return allJoinMappingByEventId.get(eventId)
			.stream()
			.filter(e -> e.getStatus().equals(ParticipationStatus.PARTICIPATING))
			.map(e -> e.getUserId())
			.collect(
				Collectors.toList());

	}
}
