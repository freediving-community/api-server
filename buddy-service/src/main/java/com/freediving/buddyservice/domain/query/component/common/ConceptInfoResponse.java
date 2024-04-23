package com.freediving.buddyservice.domain.query.component.common;

import com.freediving.buddyservice.common.enumeration.BuddyEventConcept;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ConceptInfoResponse {
	private BuddyEventConcept conceptId;
	private String conceptName;
}