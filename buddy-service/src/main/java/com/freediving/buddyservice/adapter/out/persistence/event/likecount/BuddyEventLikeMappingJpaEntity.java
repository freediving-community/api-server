package com.freediving.buddyservice.adapter.out.persistence.event.likecount;

import com.freediving.buddyservice.adapter.out.persistence.event.BuddyEventJpaEntity;
import com.freediving.common.persistence.AuditableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "buddy_event_like_mapping")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(BuddyEventLikeMappingId.class)
public class BuddyEventLikeMappingJpaEntity extends AuditableEntity {
	@Id
	private Long userId; // 복합 키의 일부

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id", referencedColumnName = "event_id", insertable = false, updatable = false)
	private BuddyEventJpaEntity buddyEvent;

	@Column(name = "is_deleted", nullable = false)
	private Boolean isDeleted;

	public BuddyEventLikeMappingJpaEntity likeToggleOn() {
		if (isDeleted == null) {
			isDeleted = false;
			return this;
		}
		isDeleted = false;
		return this;
	}

	public BuddyEventLikeMappingJpaEntity likeToggleOff() {
		if (isDeleted == null) {
			isDeleted = true;
			return this;
		}
		isDeleted = true;
		return this;
	}

}