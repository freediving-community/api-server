package com.freediving.buddyservice.application.port.out.web.query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.freediving.buddyservice.adapter.out.persistence.concept.BuddyEventConceptJpaEntity;
import com.freediving.buddyservice.config.enumerate.SortType;
import com.freediving.buddyservice.domain.enumeration.BuddyEventConcept;
import com.freediving.buddyservice.domain.query.QueryComponentListResponse;
import com.freediving.common.enumerate.DivingPool;

public interface GetBuddyEventListingPort {

	List<QueryComponentListResponse> getBuddyEventListing(Long userId, LocalDateTime eventStartDate,
		LocalDateTime eventEndDate, Set<BuddyEventConcept> buddyEventConcepts, Boolean carShareYn,
		Integer freedivingLevel, Set<DivingPool> divingPools, SortType sortType);
}
