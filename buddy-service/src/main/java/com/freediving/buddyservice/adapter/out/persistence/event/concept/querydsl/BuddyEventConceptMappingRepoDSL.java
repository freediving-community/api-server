package com.freediving.buddyservice.adapter.out.persistence.event.concept.querydsl;

import java.util.List;
import java.util.Map;

public interface BuddyEventConceptMappingRepoDSL {

	Map<Long, List<BuddyEventConceptMappingProjectDto>> findConceptMappingAllByEventIds(List<Long> ids);

}
