package com.freediving.buddyservice.application.port.out.web.query;

import java.time.LocalDateTime;
import java.util.List;

import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.caroselsimple.GetBuddyEventCarouselSimpleQueryProjectionDto;
import com.freediving.common.enumerate.DivingPool;

public interface GetBuddyEventCarouselSimplePort {

	List<GetBuddyEventCarouselSimpleQueryProjectionDto> getBuddyEventCarouselSimple(LocalDateTime eventStartDate
		, LocalDateTime eventEndDate,
		DivingPool divingPool, Long excludedEventId);

}
