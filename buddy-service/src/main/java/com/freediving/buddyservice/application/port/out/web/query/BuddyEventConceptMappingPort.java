package com.freediving.buddyservice.application.port.out.web.query;

import java.util.List;
import java.util.Map;

import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.BuddyEventConceptMappingProjectDto;

public interface BuddyEventConceptMappingPort {

	Map<Long, List<BuddyEventConceptMappingProjectDto>> getAllConceptMapping(List<Long> ids);
}
