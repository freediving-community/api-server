package com.freediving.buddyservice.adapter.out.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.freediving.buddyservice.adapter.out.persistence.util.EventConceptsListConverter;
import com.freediving.buddyservice.common.enumeration.EventConcept;
import com.freediving.buddyservice.common.enumeration.EventStatus;
import com.freediving.common.persistence.AuditableEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
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
@Table(name = "buddy_events", indexes = {
	@Index(name = "idx_userid", columnList = "userId"),
	@Index(name = "idx_eventStartDate", columnList = "eventStartDate")
})
public class BuddyEventsJpaEntity extends AuditableEntity {

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

	@Convert(converter = EventConceptsListConverter.class)
	@Column(name = "event_concepts")
	private List<EventConcept> eventConcepts;

	@Column(name = "car_share_yn", nullable = false)
	private Boolean carShareYn;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private EventStatus status;

	@Column(name = "kakao_room_code", length = 10)
	private String kakaoRoomCode;

	@Column(name = "comment", length = 1000)
	private String comment;

	// 연관 관계 매핑
	@OneToMany(mappedBy = "buddyEvents", cascade = CascadeType.ALL)
	private Set<EventsDivingPoolMapping> eventsDivingPoolMapping;

	@OneToMany(mappedBy = "buddyEvents", cascade = CascadeType.ALL)
	private Set<BuddyEventConditions> buddyEventConditions;

	@OneToMany(mappedBy = "buddyEvents", cascade = CascadeType.ALL)
	private Set<BuddyEventJoinRequests> buddyEventJoinRequests;

	@Override
	public String toString() {
		return "BuddyEventsJpaEntity{" + "eventId=" + eventId + ", userId=" + userId + ", eventStartDate="
			+ eventStartDate + ", eventEndDate=" + eventEndDate + ", participantCount=" + participantCount
			+ ", eventConcepts=" + eventConcepts + ", carShareYn=" + carShareYn + ", kakaoRoomCode=" + kakaoRoomCode
			+ ", status=" + status + ", comment='"
			+ comment + '\'' + '}';
	}
}
