package com.freediving.buddyservice.adapter.out.persistence.event.querydsl.listing;

import java.time.LocalDateTime;
import java.util.Set;

import com.freediving.buddyservice.domain.enumeration.BuddyEventStatus;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class GetBuddyEventListingQueryProjectionDto {
	private Long eventId;
	private LocalDateTime eventStartDate;
	private LocalDateTime eventEndDate;
	private boolean isLiked;
	private Integer likedCount;
	private String comment;
	private Integer freedivingLevel;
	private BuddyEventStatus status;
	private Integer participantCount;
	private Long currentParticipantCount;

	@Builder
	@QueryProjection
	public GetBuddyEventListingQueryProjectionDto(Long eventId, LocalDateTime eventStartDate,
		LocalDateTime eventEndDate,
		boolean isLiked, Integer likedCount, String comment, Integer freedivingLevel, BuddyEventStatus status,
		Integer participantCount, Long currentParticipantCount) {
		this.eventId = eventId;
		this.eventStartDate = eventStartDate;
		this.eventEndDate = eventEndDate;
		this.isLiked = isLiked;
		this.likedCount = likedCount;
		this.comment = comment;
		this.freedivingLevel = freedivingLevel;
		this.status = status;
		this.participantCount = participantCount;
		this.currentParticipantCount = currentParticipantCount;
	}
}
