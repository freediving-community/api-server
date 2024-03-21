package com.freediving.buddyservice.adapter.out.persistence.event;

import com.freediving.buddyservice.common.enumeration.FreedivingLevel;
import com.freediving.common.persistence.AuditableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "buddy_event_conditions")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class BuddyEventConditions extends AuditableEntity {

	@Id
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id", referencedColumnName = "event_id", insertable = false, updatable = false)
	private BuddyEventsJpaEntity buddyEvent;

	// 나머지 컬럼 정의 및 getters and setters
	@Column(name = "freediving_level")
	@Enumerated(value = EnumType.STRING)
	private FreedivingLevel freedivingLevel;
}