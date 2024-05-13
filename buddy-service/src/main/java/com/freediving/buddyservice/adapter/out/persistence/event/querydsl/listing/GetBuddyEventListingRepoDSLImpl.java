package com.freediving.buddyservice.adapter.out.persistence.event.querydsl.listing;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.freediving.buddyservice.adapter.out.persistence.concept.QBuddyEventConceptJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.QBuddyEventJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.concept.QBuddyEventConceptMappingJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.divingpool.QBuddyEventDivingPoolMappingJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.join.QBuddyEventJoinRequestJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.likecount.QBuddyEventLikeCountJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.likecount.QBuddyEventLikeMappingJpaEntity;
import com.freediving.buddyservice.config.enumerate.SortType;
import com.freediving.buddyservice.domain.enumeration.BuddyEventConcept;
import com.freediving.common.enumerate.DivingPool;
import com.freediving.divingpool.data.dao.QDivingPoolJpaEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class GetBuddyEventListingRepoDSLImpl implements GetBuddyEventListingRepoDSL {

	private final EntityManager entityManager;

	@Override
	public List<GetBuddyEventListingQueryProjectionDto> getBuddyEventListing(Long userId, LocalDateTime eventStartDate,
		LocalDateTime eventEndDate, Set<BuddyEventConcept> buddyEventConcepts, Boolean carShareYn,
		Integer freedivingLevel, Set<DivingPool> divingPools, SortType sortType, int pageNumber, int pageSize) {

		JPQLQueryFactory jpqlQueryFactory = new JPAQueryFactory(entityManager);
		BooleanBuilder whereClause = new BooleanBuilder();

		QBuddyEventJpaEntity event = QBuddyEventJpaEntity.buddyEventJpaEntity;
		QBuddyEventLikeMappingJpaEntity likeMapping = QBuddyEventLikeMappingJpaEntity.buddyEventLikeMappingJpaEntity;
		QBuddyEventLikeCountJpaEntity likeCount = QBuddyEventLikeCountJpaEntity.buddyEventLikeCountJpaEntity;
		QBuddyEventConceptMappingJpaEntity conceptMapping = QBuddyEventConceptMappingJpaEntity.buddyEventConceptMappingJpaEntity;
		QBuddyEventDivingPoolMappingJpaEntity divingPoolMapping = QBuddyEventDivingPoolMappingJpaEntity.buddyEventDivingPoolMappingJpaEntity;
		QBuddyEventJoinRequestJpaEntity join = QBuddyEventJoinRequestJpaEntity.buddyEventJoinRequestJpaEntity;

		// 날짜 조건 필터링 -> 컨셉
		JPQLQuery query = jpqlQueryFactory.select(
			new QGetBuddyEventListingQueryProjectionDto(
				event.eventId,                // 이벤트 ID
				event.eventStartDate,        // 이벤트 시작 시간
				event.eventEndDate,            // 이벤트 종료 시간
				likeMapping.buddyEvent.eventId.isNotNull(),    // 사용자 like여부
				likeCount.likeCount.max(),    // like 수
				event.comment,            // 코멘트
				event.freedivingLevel,    //레벨 조건
				event.status,            //상태
				event.participantCount, // 모집 인원
				join.userId.countDistinct().intValue()
			)
		).from(event);

		// 다이빙 풀 조건이 존재할 경우  하나만 해당되어도 리턴한다.
		if (divingPools != null && divingPools.isEmpty() == false)
			query.innerJoin(divingPoolMapping)
				.on(event.eventId.eq(divingPoolMapping.buddyEvent.eventId)
					.and(divingPoolMapping.divingPoolId.in(divingPools)));

		// 로그인 된 사용자라면.
		if (userId != null)
			query.leftJoin(likeMapping)
				.on(event.eventId.eq(likeMapping.buddyEvent.eventId)
					.and(likeMapping.userId.eq(userId))
					.and(likeMapping.isDeleted.eq(false)));

		int conceptCount = 0;
		query.leftJoin(conceptMapping)
			.on(event.eventId.eq(conceptMapping.buddyEvent.eventId));

		// 컨셉이 null이 아니고 빈 값이 아닌 경우

		query.leftJoin(likeCount).on(event.eventId.eq(likeCount.eventId));
		query.leftJoin(join).on(event.eventId.eq(join.buddyEvent.eventId));

		if (carShareYn != null)
			whereClause.and(event.carShareYn.eq(carShareYn));

		if (freedivingLevel != null)
			whereClause.and(event.freedivingLevel.eq(freedivingLevel));

		if (buddyEventConcepts != null && buddyEventConcepts.isEmpty() == false) {
			conceptCount = buddyEventConcepts.size();
			whereClause.and(conceptMapping.conceptId.in(buddyEventConcepts));
		}

		query.where(whereClause.and(event.eventStartDate.between(eventStartDate, eventEndDate)));

		query.groupBy(event.eventId, event.eventId, event.eventStartDate, event.eventEndDate, event.comment,
			event.freedivingLevel, event.status, event.participantCount, likeMapping.buddyEvent.eventId);

		if (buddyEventConcepts != null && buddyEventConcepts.isEmpty() == false) {
			query.having(conceptMapping.conceptId.count().eq((long)conceptCount));
		}
		query.offset((pageNumber - 1) * pageSize).limit(pageSize);

		List<GetBuddyEventListingQueryProjectionDto> response = query.fetch();

		return response;
	}

	@Override
	public Long countOfGetBuddyEventListing(Long userId,
		LocalDateTime eventStartDate,
		LocalDateTime eventEndDate, Set<BuddyEventConcept> buddyEventConcepts, Boolean carShareYn,
		Integer freedivingLevel, Set<DivingPool> divingPools, SortType sortType) {

		JPQLQueryFactory jpqlQueryFactory = new JPAQueryFactory(entityManager);
		BooleanBuilder whereClause = new BooleanBuilder();

		QBuddyEventJpaEntity event = QBuddyEventJpaEntity.buddyEventJpaEntity;
		QBuddyEventLikeCountJpaEntity likeCount = QBuddyEventLikeCountJpaEntity.buddyEventLikeCountJpaEntity;
		QBuddyEventConceptMappingJpaEntity conceptMapping = QBuddyEventConceptMappingJpaEntity.buddyEventConceptMappingJpaEntity;
		QBuddyEventDivingPoolMappingJpaEntity divingPoolMapping = QBuddyEventDivingPoolMappingJpaEntity.buddyEventDivingPoolMappingJpaEntity;

		int conceptCount = 0;

		// 2. 카운팅 쿼리.

		// 날짜 조건 필터링 -> 컨셉
		JPQLQuery countQuery = jpqlQueryFactory.select(
			event.eventId
		).from(event);

		// 다이빙 풀 조건이 존재할 경우  하나만 해당되어도 리턴한다.
		if (divingPools != null && divingPools.isEmpty() == false)
			countQuery.innerJoin(divingPoolMapping)
				.on(event.eventId.eq(divingPoolMapping.buddyEvent.eventId)
					.and(divingPoolMapping.divingPoolId.in(divingPools)));

		countQuery.leftJoin(conceptMapping)
			.on(event.eventId.eq(conceptMapping.buddyEvent.eventId));

		// 컨셉이 null이 아니고 빈 값이 아닌 경우

		countQuery.leftJoin(likeCount).on(event.eventId.eq(likeCount.eventId));

		if (carShareYn != null)
			whereClause.and(event.carShareYn.eq(carShareYn));

		if (freedivingLevel != null)
			whereClause.and(event.freedivingLevel.eq(freedivingLevel));

		if (buddyEventConcepts != null && buddyEventConcepts.isEmpty() == false) {
			conceptCount = buddyEventConcepts.size();
			whereClause.and(conceptMapping.conceptId.in(buddyEventConcepts));
		}

		countQuery.where(whereClause.and(event.eventStartDate.between(eventStartDate, eventEndDate)));

		countQuery.groupBy(event.eventId);

		if (buddyEventConcepts != null && buddyEventConcepts.isEmpty() == false) {
			countQuery.having(conceptMapping.conceptId.count().eq((long)conceptCount));
		}

		long totalCount = countQuery.fetchCount();

		return totalCount;
	}

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
