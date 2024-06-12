package com.freediving.buddyservice.application.port.out.web.query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.listing.GetBuddyEventListingQueryProjectionDto;
import com.freediving.buddyservice.config.enumerate.GenderType;
import com.freediving.buddyservice.config.enumerate.SortType;
import com.freediving.buddyservice.domain.enumeration.BuddyEventConcept;
import com.freediving.common.enumerate.DivingPool;

public interface GetBuddyEventListingPort {

	List<GetBuddyEventListingQueryProjectionDto> getBuddyEventListing(Long userId, LocalDateTime eventStartDate,
		LocalDateTime eventEndDate, Set<BuddyEventConcept> buddyEventConcepts, Boolean carShareYn,
		Integer freedivingLevel, Set<DivingPool> divingPools, SortType sortType, GenderType genderType, int pageNumber,
		int pageSize);

	Long countOfGetBuddyEventListing(Long userId, LocalDateTime eventStartDate,
		LocalDateTime eventEndDate, Set<BuddyEventConcept> buddyEventConcepts, Boolean carShareYn,
		Integer freedivingLevel, Set<DivingPool> divingPools, GenderType genderType);

	List<GetBuddyEventListingQueryProjectionDto> getLikeBuddyEventListing(Long userId, LocalDateTime now,
		int pageNumber,
		int pageSize);

	Long countOfGetLikeBuddyEventListing(Long userId, LocalDateTime now);

}
