package com.freediving.buddyservice.adapter.out.persistence.event.querydsl.listing;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.freediving.buddyservice.adapter.out.persistence.event.QBuddyEventJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.concept.QBuddyEventConceptMappingJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.divingpool.QBuddyEventDivingPoolMappingJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.join.QBuddyEventJoinRequestJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.likecount.QBuddyEventLikeCountJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.likecount.QBuddyEventLikeMappingJpaEntity;
import com.freediving.buddyservice.config.enumerate.SortType;
import com.freediving.buddyservice.domain.enumeration.BuddyEventConcept;
import com.freediving.common.enumerate.DivingPool;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetBuddyEventListingRepoDSLImpl implements GetBuddyEventListingRepoDSL {

	private final JPQLQueryFactory jpqlQueryFactory;
	BooleanBuilder whereClause = new BooleanBuilder();

	@Override
	public List<GetBuddyEventListingQueryProjectionDto> getBuddyEventListing(Long userId, LocalDateTime eventStartDate,
		LocalDateTime eventEndDate, Set<BuddyEventConcept> buddyEventConcepts, Boolean carShareYn,
		Integer freedivingLevel, Set<DivingPool> divingPools, SortType sortType) {

		QBuddyEventJpaEntity event = QBuddyEventJpaEntity.buddyEventJpaEntity;
		QBuddyEventLikeMappingJpaEntity likeMapping = QBuddyEventLikeMappingJpaEntity.buddyEventLikeMappingJpaEntity;
		QBuddyEventLikeCountJpaEntity likeCount = QBuddyEventLikeCountJpaEntity.buddyEventLikeCountJpaEntity;
		QBuddyEventConceptMappingJpaEntity conceptMapping = QBuddyEventConceptMappingJpaEntity.buddyEventConceptMappingJpaEntity;
		QBuddyEventDivingPoolMappingJpaEntity divingPoolMapping = QBuddyEventDivingPoolMappingJpaEntity.buddyEventDivingPoolMappingJpaEntity;
		QBuddyEventJoinRequestJpaEntity join = QBuddyEventJoinRequestJpaEntity.buddyEventJoinRequestJpaEntity;



		// 날짜 조건 필터링 -> 컨셉
		JPQLQuery query =  jpqlQueryFactory.select(
			new QGetBuddyEventListingQueryProjectionDto(
				event.eventId,				// 이벤트 ID
				event.eventStartDate,		// 이벤트 시작 시간
				event.eventEndDate,			// 이벤트 종료 시간
				likeMapping.buddyEvent.eventId.isNotNull().isTrue(),	// 사용자 like여부
				likeCount.likeCount,	// like 수
				event.comment,			// 코멘트
				event.freedivingLevel,	//레벨 조건
				event.status,			//상태
				event.participantCount, // 모집 인원
				join.userId.count()
			)
		).from(event);



		// 로그인 된 사용자라면.
		if(userId != null)
			query.leftJoin(likeMapping)
				.on(event.eventId.eq(likeMapping.buddyEvent.eventId)
					.and(likeMapping.userId.eq(userId))
					.and(likeMapping.isDeleted.eq(false)));

		int conceptCount = 0;

		// 컨셉이 null이 아니고 빈 값이 아닌 경우
		if(buddyEventConcepts != null&& buddyEventConcepts.isEmpty() == false) {
			conceptCount = buddyEventConcepts.size();

			query.leftJoin(conceptMapping)
				.on(event.eventId.eq(conceptMapping.buddyEvent.eventId));
		}

		// 다이빙 풀 조건이 존재할 경우  하나만 해당되어도 리턴한다.
		if( divingPools != null && divingPools.isEmpty() == false)
			query.innerJoin(divingPoolMapping)
				.on(event.eventId.eq(divingPoolMapping.buddyEvent.eventId)
					.and(divingPoolMapping.divingPoolId.in(divingPools)));

		query.leftJoin(likeCount).on(event.eventId.eq(likeCount.eventId));
		query.leftJoin(join).on(event.eventId.eq(join.buddyEvent.eventId));

		if(carShareYn != null)
			whereClause.and(event.carShareYn.eq(carShareYn));

		if(freedivingLevel != null)
			whereClause.and(event.freedivingLevel.eq(freedivingLevel));

		query.where(whereClause.and(event.eventStartDate.between(eventStartDate,eventEndDate)));

		query.groupBy(event.eventId,event.eventId, event.eventStartDate, event.eventEndDate,event.comment,event.freedivingLevel, event.status,event.participantCount)
			.having(conceptMapping.conceptId.count().eq((long) conceptCount));
		 List<GetBuddyEventListingQueryProjectionDto> response =   query.fetch();


		return null;
	}
}
