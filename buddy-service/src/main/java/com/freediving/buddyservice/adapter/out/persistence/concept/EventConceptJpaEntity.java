package com.freediving.buddyservice.adapter.out.persistence.concept;

import com.freediving.buddyservice.common.enumeration.EventConcept;
import com.freediving.common.persistence.AuditableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Table(name = "buddy_event_concept")
public class EventConceptJpaEntity extends AuditableEntity {

	@Id
	@Enumerated(EnumType.STRING)
	@Column(name = "concept_id")
	private EventConcept conceptId;

	@Column(name = "concept_name", nullable = false)
	private String conceptName;

	@Column(name = "enabled", nullable = false)
	private Boolean enabled;

	@Column(name = "display_order", nullable = false)
	private Integer displayOrder;

	@Override
	public String toString() {
		return "EventConceptJpaEntity{" +
			"conceptId=" + conceptId +
			", conceptName='" + conceptName + '\'' +
			", enabled=" + enabled +
			", displayOrder=" + displayOrder +
			'}';
	}
}
