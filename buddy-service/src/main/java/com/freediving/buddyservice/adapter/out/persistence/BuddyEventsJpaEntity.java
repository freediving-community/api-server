package com.freediving.buddyservice.adapter.out.persistence;

import java.time.LocalDateTime;
import java.util.List;

import com.freediving.buddyservice.common.enumeration.EventConcept;
import com.freediving.buddyservice.common.enumeration.EventStatus;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "buddy_events")
public class BuddyEventsJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eventId;

	private Long userId;

	private LocalDateTime eventStartTime;
	private LocalDateTime eventEndTime;

	private Integer participantCount;

	@Convert(converter = EventConceptsListConverter.class)
	private List<EventConcept> eventConcepts;

	private Boolean carShareYn;

	@Enumerated(EnumType.STRING)
	private EventStatus status;

	private String comment;

	@Override
	public String toString() {
		return "BuddyEventsJpaEntity{" +
			"eventId=" + eventId +
			", userId=" + userId +
			", eventStartTime=" + eventStartTime +
			", eventEndTime=" + eventEndTime +
			", participantCount=" + participantCount +
			", eventConcepts=" + eventConcepts +
			", carShareYn=" + carShareYn +
			", status=" + status +
			", comment='" + comment + '\'' +
			'}';
	}
}
