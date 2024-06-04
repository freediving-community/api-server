package com.freediving.buddyservice.adapter.out.persistence.event.divingpool.querydsl;

import java.util.List;
import java.util.Map;

public interface BuddyEventDivingPoolMappingRepoDSL {

	Map<Long, List<BuddyEventDivingPoolMappingProjectDto>> findDivingPoolMappingAllByEventIds(List<Long> ids);

}
