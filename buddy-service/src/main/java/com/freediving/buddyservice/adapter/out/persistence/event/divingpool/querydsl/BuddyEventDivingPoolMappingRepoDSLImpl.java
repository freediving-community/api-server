package com.freediving.buddyservice.adapter.out.persistence.event.divingpool.querydsl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.freediving.buddyservice.adapter.out.persistence.event.divingpool.QBuddyEventDivingPoolMappingJpaEntity;
import com.freediving.divingpool.data.dao.QDivingPoolJpaEntity;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BuddyEventDivingPoolMappingRepoDSLImpl implements BuddyEventDivingPoolMappingRepoDSL {

	private final EntityManager entityManager;

	@Override
	public Map<Long, List<BuddyEventDivingPoolMappingProjectDto>> findDivingPoolMappingAllByEventIds(List<Long> ids) {
		QBuddyEventDivingPoolMappingJpaEntity qDivingPoolMapping = QBuddyEventDivingPoolMappingJpaEntity.buddyEventDivingPoolMappingJpaEntity;
		QDivingPoolJpaEntity qDivingPool = QDivingPoolJpaEntity.divingPoolJpaEntity;
		JPQLQueryFactory jpqlQueryFactory = new JPAQueryFactory(entityManager);

		List<BuddyEventDivingPoolMappingProjectDto> results = jpqlQueryFactory
			.select(new QBuddyEventDivingPoolMappingProjectDto(qDivingPoolMapping.buddyEvent.eventId,
				qDivingPool.divingPoolId,
				qDivingPool.divingPoolName
			))
			.from(qDivingPoolMapping)
			.innerJoin(qDivingPool)
			.on(qDivingPoolMapping.divingPoolId.eq(qDivingPool.divingPoolId))
			.where(qDivingPoolMapping.buddyEvent.eventId.in(ids))
			.fetch();

		return results.stream()
			.collect(Collectors.groupingBy(BuddyEventDivingPoolMappingProjectDto::getEventId));
	}

}
