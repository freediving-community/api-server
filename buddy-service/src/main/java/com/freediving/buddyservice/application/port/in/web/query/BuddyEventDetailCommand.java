package com.freediving.buddyservice.application.port.in.web.query;

import com.freediving.common.SelfValidating;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
@Getter
public class BuddyEventDetailCommand extends SelfValidating<BuddyEventDetailCommand> {

	private final Long userId;
	private final Long eventId;

	@Builder
	public BuddyEventDetailCommand(Long userId, Long eventId) {

		this.eventId = eventId;
		this.userId = userId;

		this.validateSelf();
	}
}
