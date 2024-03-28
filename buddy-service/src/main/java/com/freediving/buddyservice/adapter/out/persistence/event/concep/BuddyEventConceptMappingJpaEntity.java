package com.freediving.buddyservice.adapter.out.persistence.event.concep;

import com.freediving.buddyservice.adapter.out.persistence.event.BuddyEventJpaEntity;
import com.freediving.buddyservice.common.enumeration.BuddyEventConcept;
import com.freediving.common.persistence.AuditableEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "buddy_event_concept_mapping")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(BuddyEventConceptMappingId.class)
public class BuddyEventConceptMappingJpaEntity extends AuditableEntity {

	@Id
	@ManyToOne
	@JoinColumn(name = "event_id", referencedColumnName = "event_id", insertable = false, updatable = false)
	private BuddyEventJpaEntity buddyEvent;

	@Id
	@Enumerated(EnumType.STRING)
	private BuddyEventConcept conceptId; // 복합 키의 일부

}