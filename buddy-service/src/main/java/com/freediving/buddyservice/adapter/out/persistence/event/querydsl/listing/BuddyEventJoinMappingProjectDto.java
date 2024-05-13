package com.freediving.buddyservice.adapter.out.persistence.event.querydsl.listing;

import com.freediving.buddyservice.domain.enumeration.ParticipationStatus;
import com.querydsl.core.annotations.QueryProjection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class BuddyEventJoinMappingProjectDto {
	private Long eventId;
	private Long userId;
	private ParticipationStatus status;

	@QueryProjection
	public BuddyEventJoinMappingProjectDto(Long eventId, Long userId, ParticipationStatus status) {
		this.eventId = eventId;
		this.userId = userId;
		this.status = status;
	}
}
