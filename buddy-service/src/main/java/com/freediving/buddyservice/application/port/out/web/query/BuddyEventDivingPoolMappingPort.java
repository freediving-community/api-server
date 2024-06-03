package com.freediving.buddyservice.application.port.out.web.query;

import java.util.List;
import java.util.Map;

import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.BuddyEventDivingPoolMappingProjectDto;

public interface BuddyEventDivingPoolMappingPort {

	Map<Long, List<BuddyEventDivingPoolMappingProjectDto>> getAllDivingPoolMapping(List<Long> ids);
}
