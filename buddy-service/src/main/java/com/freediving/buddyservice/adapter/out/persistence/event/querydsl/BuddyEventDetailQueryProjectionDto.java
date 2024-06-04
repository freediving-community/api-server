package com.freediving.buddyservice.adapter.out.persistence.event.querydsl;

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
	name = "BuddyEventDetailQueryProjectionDto",
	classes = @ConstructorResult(
		targetClass = BuddyEventDetailQueryProjectionDto.class,
		columns = {
			@ColumnResult(name = "userId", type = Long.class),
			@ColumnResult(name = "eventId", type = Long.class),
			@ColumnResult(name = "eventStartDate", type = LocalDateTime.class),
			@ColumnResult(name = "eventEndDate", type = LocalDateTime.class),
			@ColumnResult(name = "genderType", type = String.class),
			@ColumnResult(name = "freedivingLevel", type = Integer.class),
			@ColumnResult(name = "participantCount", type = Integer.class),
			@ColumnResult(name = "currentParticipantCount", type = Integer.class),
			@ColumnResult(name = "isLiked", type = Boolean.class),
			@ColumnResult(name = "likeCount", type = Integer.class),
			@ColumnResult(name = "viewCount", type = Integer.class),
			@ColumnResult(name = "imageUrl", type = String.class),
			@ColumnResult(name = "comment", type = String.class),
			@ColumnResult(name = "status", type = String.class),
			@ColumnResult(name = "carShareYn", type = Boolean.class)
		}
	)
)
public class BuddyEventDetailQueryProjectionDto {
	private Long userId;
	private Long eventId;
	private LocalDateTime eventStartDate;
	private LocalDateTime eventEndDate;
	private GenderType genderType;
	private Long freedivingLevel;
	private Long participantCount;
	private Long currentParticipantCount;
	private boolean isLiked;
	private Long likedCount;
	private Long viewCount;
	private String imageUrl;
	private String comment;
	private BuddyEventStatus status;
	private boolean carShareYn;

	@Builder
	@QueryProjection
	public BuddyEventDetailQueryProjectionDto(Long userId, Long eventId, LocalDateTime eventStartDate,
		LocalDateTime eventEndDate,
		GenderType genderType, Long freedivingLevel, Long participantCount, Long currentParticipantCount,
		boolean isLiked,
		Long likedCount, Long viewCount, String imageUrl, String comment, BuddyEventStatus status, boolean carShareYn) {
		this.userId = userId;
		this.eventId = eventId;
		this.eventStartDate = eventStartDate;
		this.eventEndDate = eventEndDate;
		this.genderType = genderType;
		this.freedivingLevel = freedivingLevel;
		this.participantCount = participantCount;
		this.currentParticipantCount = currentParticipantCount;
		this.isLiked = isLiked;
		this.likedCount = likedCount;
		this.viewCount = viewCount;
		this.imageUrl = imageUrl;
		this.comment = comment;
		this.status = status;
		this.carShareYn = carShareYn;
	}
}
