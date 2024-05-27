package com.freediving.buddyservice.application.port.out.web.query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.BuddyEventConceptMappingProjectDto;
import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.BuddyEventDivingPoolMappingProjectDto;
import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.carousel.GetBuddyEventCarouselQueryProjectionDto;
import com.freediving.common.enumerate.DivingPool;

public interface GetBuddyEventCarouselPort {

	List<GetBuddyEventCarouselQueryProjectionDto> getBuddyEventWeekly(Long userId, LocalDateTime eventStartDate,
		int pageNumber,
		int pageSize);

	Long countOfGetBuddyEventWeekly(Long userId, LocalDateTime eventStartDate);

	List<GetBuddyEventCarouselQueryProjectionDto> getBuddyEventCarouselByDivingPool(Long userId,
		LocalDateTime eventStartDate,
		DivingPool divingPool);

	Map<Long, List<BuddyEventDivingPoolMappingProjectDto>> getAllDivingPoolMapping(List<Long> ids);

	Map<Long, List<BuddyEventConceptMappingProjectDto>> getAllConceptMapping(List<Long> ids);

}
