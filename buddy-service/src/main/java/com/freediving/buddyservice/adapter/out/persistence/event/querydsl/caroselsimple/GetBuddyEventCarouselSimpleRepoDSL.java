package com.freediving.buddyservice.adapter.out.persistence.event.querydsl.caroselsimple;

import java.time.LocalDateTime;
import java.util.List;

import com.freediving.common.enumerate.DivingPool;

public interface GetBuddyEventCarouselSimpleRepoDSL {

	/**
	 * 다이빙 풀, 또는 시작~끝 시간으로 캐로셀 심플 카드 타입의 버디 모임 이벤트를 최대 10개 조회한다.
	 * @return GetBuddyEventCarouselSimpleQueryProjectionDto 필터링 된 객체
	 */

	List<GetBuddyEventCarouselSimpleQueryProjectionDto> getBuddyEventCarouselSimple(
		LocalDateTime eventStartDate
		, LocalDateTime eventEndDate
		, DivingPool divingPool
		, Long excludedEventId);

}
