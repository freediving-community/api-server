package com.freediving.buddyservice.adapter.out.persistence;

import com.freediving.buddyservice.common.enumeration.ParticipationStatus;
import com.freediving.common.persistence.AuditableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
@Table(name = "buddy_event_join_requests")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(BuddyEventJoinRequestId.class)
public class BuddyEventJoinRequests extends AuditableEntity {
	@Id
	private Long userId; // 복합 키의 일부

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id", referencedColumnName = "event_id", insertable = false, updatable = false)
	private BuddyEventsJpaEntity buddyEvents;

	@Column(name = "status")
	@Enumerated(value = EnumType.STRING)
	private ParticipationStatus status;

}