package com.freediving.buddyservice.adapter.out.persistence.event.querydsl.listing;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.freediving.buddyservice.config.enumerate.GenderType;
import com.freediving.buddyservice.config.enumerate.SortType;
import com.freediving.buddyservice.domain.enumeration.BuddyEventConcept;
import com.freediving.common.enumerate.DivingPool;

public interface GetBuddyEventListingRepoDSL {

	/**
	 * 버디 매칭 리스팅 조회 한다.
	 *
	 * 조건 : 이벤트 시간, 컨셉, 차량 공유 여부, 프리다이빙 레벨 제한, 다이빙 풀, 정렬 타입
	 *
	 * Like 정보 ( 유저의 Like 여부, Like 수 )
	 *
	 * @return GetBuddyEventListingQueryProjectionDto 필터링 된 객체
	 */

	List<GetBuddyEventListingQueryProjectionDto> getBuddyEventListing(Long userId, LocalDateTime eventStartDate,
		LocalDateTime eventEndDate, Set<BuddyEventConcept> buddyEventConcepts, Boolean carShareYn,
		Integer freedivingLevel, Set<DivingPool> divingPools, SortType sortType, GenderType genderType, int pageNumber,
		int offset);

	Long countOfGetBuddyEventListing(Long userId, LocalDateTime eventStartDate,
		LocalDateTime eventEndDate, Set<BuddyEventConcept> buddyEventConcepts, Boolean carShareYn,
		Integer freedivingLevel, Set<DivingPool> divingPools, GenderType genderType);

	List<GetBuddyEventListingQueryProjectionDto> getLikeBuddyEventListing(Long userId, LocalDateTime now,
		int pageNumber,
		int offset);

	Long countOfGetLikeBuddyEventListing(Long userId, LocalDateTime now);

}
