package com.freediving.buddyservice.adapter.out.persistence.event.querydsl.carousel;

import java.time.LocalDateTime;

import com.freediving.buddyservice.config.enumerate.GenderType;
import com.freediving.buddyservice.domain.enumeration.BuddyEventStatus;
import com.querydsl.core.annotations.QueryProjection;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@SqlResultSetMapping(
	name = "GetBuddyEventListingQueryProjectionDtoMapping",
	classes = @ConstructorResult(
		targetClass = GetBuddyEventCarouselQueryProjectionDto.class,
		columns = {
			@ColumnResult(name = "eventId", type = Long.class),
			@ColumnResult(name = "userId", type = Long.class),
			@ColumnResult(name = "eventStartDate", type = LocalDateTime.class),
			@ColumnResult(name = "eventEndDate", type = LocalDateTime.class),
			@ColumnResult(name = "isLiked", type = Boolean.class),
			@ColumnResult(name = "likeCount", type = Integer.class),
			@ColumnResult(name = "comment", type = String.class),
			@ColumnResult(name = "freedivingLevel", type = Integer.class),
			@ColumnResult(name = "status", type = String.class),
			@ColumnResult(name = "participantCount", type = Integer.class),
			@ColumnResult(name = "currentParticipantCount", type = Integer.class),
			@ColumnResult(name = "genderType", type = String.class)
		}
	)
)
public class GetBuddyEventCarouselQueryProjectionDto {
	private Long eventId;
	private Long userId;
	private LocalDateTime eventStartDate;
	private LocalDateTime eventEndDate;
	private boolean isLiked;
	private Long likedCount;
	private String comment;
	private Long freedivingLevel;
	private BuddyEventStatus status;
	private Long participantCount;
	private Long currentParticipantCount;
	private GenderType genderType;

	@Builder
	@QueryProjection
	public GetBuddyEventCarouselQueryProjectionDto(Long eventId,
		LocalDateTime eventStartDate,
		LocalDateTime eventEndDate,
		boolean isLiked, Long likedCount
		, String comment
		, Long freedivingLevel
		, BuddyEventStatus status,
		Long participantCount,
		Long currentParticipantCount,
		GenderType genderType, Long userId) {
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
		this.genderType = genderType;
		this.userId = userId;
	}
}
