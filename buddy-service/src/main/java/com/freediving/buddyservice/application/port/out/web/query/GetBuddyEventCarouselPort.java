package com.freediving.buddyservice.application.port.out.web.query;

import java.time.LocalDateTime;
import java.util.List;

import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.carousel.GetBuddyEventCarouselQueryProjectionDto;
import com.freediving.common.enumerate.DivingPool;

public interface GetBuddyEventCarouselPort {

	List<GetBuddyEventCarouselQueryProjectionDto> getBuddyEventWeekly(Long userId, LocalDateTime eventStartDate,
		int pageNumber,
		int pageSize);

	Long countOfGetBuddyEventWeekly(Long userId, LocalDateTime eventStartDate);

	List<GetBuddyEventCarouselQueryProjectionDto> getHomePreferencePoolBuddyEvent(Long userId,
		LocalDateTime eventStartDate,
		DivingPool divingPool);

	List<GetBuddyEventCarouselQueryProjectionDto> getHomeActiveBuddyEvent(Long userId, LocalDateTime eventStartDate,
		int pageNumber, int pageSize);

	Long countOfGetHomeActiveBuddyEvent(Long userId, LocalDateTime eventStartDate);

}
