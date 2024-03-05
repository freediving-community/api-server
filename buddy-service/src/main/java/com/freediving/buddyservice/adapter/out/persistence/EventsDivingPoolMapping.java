package com.freediving.buddyservice.adapter.out.persistence;

import com.freediving.common.enumerate.DivingPool;
import com.freediving.common.persistence.AuditableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "events_diving_pool_mapping")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(EventsDivingPoolMappingId.class)
public class EventsDivingPoolMapping extends AuditableEntity {
	@Id
	private DivingPool divingPoolId; // 복합 키의 일부

	@Id
	@Column(name = "event_id")
	private Long eventId; // 복합 키이면서 외래 키로 사용

	@ManyToOne
	@JoinColumn(name = "event_id", referencedColumnName = "event_id", insertable = false, updatable = false)
	private BuddyEventsJpaEntity buddyEvents;

}