package com.freediving.buddyservice.common.task.domain;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BuddyEventLikeToggleTask {

	private Long userId;
	private Long eventId;
	private boolean likeStatus;
}
