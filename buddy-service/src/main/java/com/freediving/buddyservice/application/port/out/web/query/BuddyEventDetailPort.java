package com.freediving.buddyservice.application.port.out.web.query;

import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.BuddyEventDetailQueryProjectionDto;

public interface BuddyEventDetailPort {

	BuddyEventDetailQueryProjectionDto getBuddyEventDetail(Long userId, Long eventId);

}
