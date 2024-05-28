package com.freediving.buddyservice.adapter.out.persistence.event.querydsl.carousel;

import java.time.LocalDateTime;
import java.util.List;

import com.freediving.common.enumerate.DivingPool;

public interface GetBuddyEventCarouselRepoDSL {

	/**
	 * 시작 날짜로부터 버디 모임을 조회 한다.
	 * Like 정보 ( 유저의 Like 여부, Like 수 )
	 * @return GetBuddyEventCarouselQueryProjectionDto 필터링 된 객체
	 */

	List<GetBuddyEventCarouselQueryProjectionDto> getBuddyEventCarousel(Long userId, LocalDateTime eventStartDate,
		int pageNumber,
		int offset);

	Long countOfGetBuddyEventCarousel(Long userId, LocalDateTime eventStartDate);

	Long countOfGetHomeActiveBuddyEvent(Long userId, LocalDateTime eventStartDate);

	List<GetBuddyEventCarouselQueryProjectionDto> getHomePreferencePoolBuddyEvent(Long userId,
		LocalDateTime eventStartDate,
		DivingPool divingPool);

	List<GetBuddyEventCarouselQueryProjectionDto> getHomeActiveBuddyEvent(Long userId, LocalDateTime eventStartDate,
		int pageNumber,
		int offset);
}
