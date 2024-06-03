package com.freediving.buddyservice.adapter.out.persistence.event.join.querydsl;

import java.util.List;
import java.util.Map;

import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.listing.BuddyEventJoinMappingProjectDto;

public interface BuddyEventJoinRepoDSL {

	Map<Long, List<BuddyEventJoinMappingProjectDto>> findJoinMappingAllByEventIds(List<Long> ids);

}
