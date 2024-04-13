package com.freediving.buddyservice.adapter.out.persistence.event.viewcount;

import com.freediving.buddyservice.adapter.out.persistence.event.BuddyEventJpaEntity;
import com.freediving.common.persistence.CreatedDateEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "buddy_event_view_count")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class BuddyEventViewCountJpaEntity extends CreatedDateEntity {

	@Id
	private Long eventId; // BuddyEventJpaEntity의 ID와 동일

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId // 이 줄을 추가
	@JoinColumn(name = "event_id", referencedColumnName = "event_id", insertable = false, updatable = false)
	private BuddyEventJpaEntity buddyEvent;

	// 나머지 컬럼 정의 및 getters and setters
	@Column(name = "view_count", nullable = false)
	private Integer viewCount;
}