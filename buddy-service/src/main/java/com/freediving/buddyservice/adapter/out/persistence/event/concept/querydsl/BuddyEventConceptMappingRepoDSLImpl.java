package com.freediving.buddyservice.adapter.out.persistence.event.concept.querydsl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.freediving.buddyservice.adapter.out.persistence.concept.QBuddyEventConceptJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.concept.QBuddyEventConceptMappingJpaEntity;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BuddyEventConceptMappingRepoDSLImpl implements BuddyEventConceptMappingRepoDSL {

	private final EntityManager entityManager;

	@Override
	public Map<Long, List<BuddyEventConceptMappingProjectDto>> findConceptMappingAllByEventIds(List<Long> ids) {
		QBuddyEventConceptMappingJpaEntity qConceptMapping = QBuddyEventConceptMappingJpaEntity.buddyEventConceptMappingJpaEntity;
		QBuddyEventConceptJpaEntity qBuddyEventConcept = QBuddyEventConceptJpaEntity.buddyEventConceptJpaEntity;

		JPQLQueryFactory jpqlQueryFactory = new JPAQueryFactory(entityManager);

		List<BuddyEventConceptMappingProjectDto> results = jpqlQueryFactory
			.select(new QBuddyEventConceptMappingProjectDto(
				qConceptMapping.buddyEvent.eventId,
				qConceptMapping.conceptId,
				qBuddyEventConcept.conceptName
			))
			.from(qConceptMapping)
			.innerJoin(qBuddyEventConcept)
			.on(qConceptMapping.conceptId.eq(qBuddyEventConcept.conceptId))
			.where(qConceptMapping.buddyEvent.eventId.in(ids))
			.fetch();

		return results.stream()
			.collect(Collectors.groupingBy(BuddyEventConceptMappingProjectDto::getEventId));
	}

}
