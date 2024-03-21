package com.freediving.buddyservice.adapter.out.persistence;

import java.io.Serializable;

import com.freediving.buddyservice.common.enumeration.EventConcept;

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
public class EventsConceptMappingId implements Serializable {
	private EventConcept conceptId; // 복합 키의 일부
	private Long buddyEvent; // 복합 키이면서 외래 키
}
