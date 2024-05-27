package com.freediving.buddyservice.adapter.out.persistence.event.querydsl.carousel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.BuddyEventConceptMappingProjectDto;
import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.BuddyEventDivingPoolMappingProjectDto;
import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.listing.BuddyEventJoinMappingProjectDto;

public interface GetBuddyEventCarouselRepoDSL {

	/**
	 * 버디 매칭 리스팅 조회 한다.
	 *
	 * 조건 : 이벤트 시간, 컨셉, 차량 공유 여부, 프리다이빙 레벨 제한, 다이빙 풀, 정렬 타입
	 *
	 * Like 정보 ( 유저의 Like 여부, Like 수 )
	 *
	 * @return GetBuddyEventListingQueryProjectionDto 필터링 된 객체
	 */

	List<GetBuddyEventCarouselQueryProjectionDto> getBuddyEventCarousel(Long userId, LocalDateTime eventStartDate,
		int pageNumber,
		int offset);

	Long countOfGetBuddyEventCarousel(Long userId, LocalDateTime eventStartDate);

	Map<Long, List<BuddyEventConceptMappingProjectDto>> findConceptMappingAllByEventIds(List<Long> ids);

	Map<Long, List<BuddyEventDivingPoolMappingProjectDto>> findDivingPoolMappingAllByEventIds(List<Long> ids);

	Map<Long, List<BuddyEventJoinMappingProjectDto>> findJoinMappingAllByEventIds(List<Long> ids);

}
