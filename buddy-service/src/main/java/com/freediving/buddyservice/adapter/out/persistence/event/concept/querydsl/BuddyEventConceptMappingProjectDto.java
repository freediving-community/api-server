package com.freediving.buddyservice.adapter.out.persistence.event.concept.querydsl;

import com.freediving.buddyservice.domain.enumeration.BuddyEventConcept;
import com.querydsl.core.annotations.QueryProjection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class BuddyEventConceptMappingProjectDto {
	private Long eventId;
	private BuddyEventConcept conceptId;
	private String conceptName;

	@QueryProjection
	public BuddyEventConceptMappingProjectDto(Long eventId, BuddyEventConcept conceptId, String conceptName) {
		this.eventId = eventId;
		this.conceptId = conceptId;
		this.conceptName = conceptName;
	}
}
