package com.freediving.buddyservice.adapter.out.persistence.event.concept;

import java.io.Serializable;

import com.freediving.buddyservice.domain.enumeration.BuddyEventConcept;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuddyEventConceptMappingId implements Serializable {
	private BuddyEventConcept conceptId; // 복합 키의 일부
	private Long buddyEvent; // 복합 키이면서 외래 키
}
