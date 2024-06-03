package com.freediving.buddyservice.adapter.out.persistence.event.join.querydsl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.freediving.buddyservice.adapter.out.persistence.event.join.QBuddyEventJoinRequestJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.listing.BuddyEventJoinMappingProjectDto;
import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.listing.QBuddyEventJoinMappingProjectDto;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BuddyEventJoinRepoDSLImpl implements BuddyEventJoinRepoDSL {

	private final EntityManager entityManager;

	/**
	 * 이벤트에 조인된 사용자를 모두 조회한다.
	 * 상태 체크는 따로 없고 모두 조회한다.
	 *
	 * @return desc
	 */

	@Override
	public Map<Long, List<BuddyEventJoinMappingProjectDto>> findJoinMappingAllByEventIds(List<Long> ids) {
		QBuddyEventJoinRequestJpaEntity qBuddyEventJoin = QBuddyEventJoinRequestJpaEntity.buddyEventJoinRequestJpaEntity;
		JPQLQueryFactory jpqlQueryFactory = new JPAQueryFactory(entityManager);

		List<BuddyEventJoinMappingProjectDto> results = jpqlQueryFactory
			.select(new QBuddyEventJoinMappingProjectDto(qBuddyEventJoin.buddyEvent.eventId,
				qBuddyEventJoin.userId,
				qBuddyEventJoin.status
			))
			.from(qBuddyEventJoin)
			.where(qBuddyEventJoin.buddyEvent.eventId.in(ids))
			.fetch();

		return results.stream()
			.collect(Collectors.groupingBy(BuddyEventJoinMappingProjectDto::getEventId));
	}

}
