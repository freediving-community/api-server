package com.freediving.buddyservice.adapter.out.persistence.event;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.DynamicUpdate;

import com.freediving.buddyservice.adapter.out.persistence.event.concept.BuddyEventConceptMappingJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.condition.BuddyEventConditionsJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.divingpool.BuddyEventDivingPoolMappingJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.join.BuddyEventJoinRequestJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.likecount.BuddyEventLikeCountJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.viewcount.BuddyEventViewCountJpaEntity;
import com.freediving.buddyservice.common.enumeration.BuddyEventStatus;
import com.freediving.common.persistence.AuditableEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@DynamicUpdate
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Table(name = "buddy_event", indexes = {
	@Index(name = "idx_userid", columnList = "userId"),
	@Index(name = "idx_eventStartDate", columnList = "eventStartDate")
})
public class BuddyEventJpaEntity extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "event_id")
	private Long eventId;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "event_start_date", nullable = false)
	private LocalDateTime eventStartDate;

	@Column(name = "event_end_date", nullable = false)
	private LocalDateTime eventEndDate;

	@Column(name = "participant_count", nullable = false)
	private Integer participantCount;

	@Column(name = "car_share_yn", nullable = false)
	private Boolean carShareYn;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private BuddyEventStatus status;

	@Column(name = "kakao_room_code", length = 10, nullable = true)
	private String kakaoRoomCode;

	@Column(name = "comment", length = 1000, nullable = true)
	private String comment;

	// 연관 관계 매핑

	@OneToMany(mappedBy = "buddyEvent", cascade = {CascadeType.ALL})
	private Set<BuddyEventConceptMappingJpaEntity> eventConcepts;

	@OneToMany(mappedBy = "buddyEvent", cascade = CascadeType.ALL)
	private Set<BuddyEventDivingPoolMappingJpaEntity> buddyEventDivingPoolMappingJpaEntity;

	@OneToOne(mappedBy = "buddyEvent", cascade = CascadeType.ALL)
	private BuddyEventConditionsJpaEntity buddyEventConditionsJpaEntity;

	@OneToMany(mappedBy = "buddyEvent", cascade = CascadeType.ALL)
	private Set<BuddyEventJoinRequestJpaEntity> buddyEventJoinRequests;

	@OneToOne(mappedBy = "buddyEvent", cascade = CascadeType.PERSIST)
	private BuddyEventLikeCountJpaEntity buddyEventLikeCountJpaEntity;

	@OneToOne(mappedBy = "buddyEvent", cascade = CascadeType.PERSIST)
	private BuddyEventViewCountJpaEntity buddyEventViewCountJpaEntity;

	public BuddyEventJpaEntity changeBuddyEventConceptMapping(Set<BuddyEventConceptMappingJpaEntity> target) {
		this.eventConcepts = target;
		return this;
	}

	public BuddyEventJpaEntity changeBuddyEventDivingPoolMapping(Set<BuddyEventDivingPoolMappingJpaEntity> target) {
		this.buddyEventDivingPoolMappingJpaEntity = target;
		return this;
	}

	public BuddyEventJpaEntity changeBuddyEventConditions(BuddyEventConditionsJpaEntity target) {
		this.buddyEventConditionsJpaEntity = target;
		return this;
	}

	public BuddyEventJpaEntity changeBuddyEventJoinRequests(Set<BuddyEventJoinRequestJpaEntity> target) {
		this.buddyEventJoinRequests = target;
		return this;
	}

	@Override
	public String toString() {
		return "BuddyEventJpaEntity{" + "eventId=" + eventId + ", userId=" + userId + ", eventStartDate="
			+ eventStartDate + ", eventEndDate=" + eventEndDate + ", participantCount=" + participantCount
			+ ", buddyEventConcepts=" + eventConcepts + ", carShareYn=" + carShareYn + ", kakaoRoomCode="
			+ kakaoRoomCode
			+ ", status=" + status + ", comment='"
			+ comment + '\'' + '}';
	}
}
