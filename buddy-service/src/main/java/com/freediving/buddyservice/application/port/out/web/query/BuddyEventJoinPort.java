package com.freediving.buddyservice.application.port.out.web.query;

import java.util.List;
import java.util.Map;

import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.listing.BuddyEventJoinMappingProjectDto;

public interface BuddyEventJoinPort {

	List<Long> getParticipantsOfEvent(Long eventId);

	Map<Long, List<BuddyEventJoinMappingProjectDto>> getAllJoinMapping(List<Long> ids);
}
