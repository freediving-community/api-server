package com.freediving.buddyservice.application.port.in.command.like;

import com.freediving.common.SelfValidating;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
* BuddyEventLikeToggleCommand 서비스 레이어로 전달하는 명령 객체
* 서비스 비즈니스 유효성 체크를 진행한다.
 *
* @author pus__
* @version 1.0.0
* 작성일 2024-04-10
**/

@EqualsAndHashCode(callSuper = false)
@Getter
public class BuddyEventLikeToggleCommand extends SelfValidating<BuddyEventLikeToggleCommand> {

	@NotNull
	private final Long userId;

	@Positive
	private final Long eventId;

	private final boolean likeStatus;

	@Builder
	public BuddyEventLikeToggleCommand(Long userId, Long eventId, boolean likeStatus) {
		this.userId = userId;
		this.eventId = eventId;
		this.likeStatus = likeStatus;
		this.validateSelf();
	}
}
