package com.freediving.buddyservice.application.port.in.web.query.home;

import java.time.LocalDateTime;

import com.freediving.common.SelfValidating;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author pus__
 * @version 1.0.0
 * 작성일 2024-05-05
 **/
@EqualsAndHashCode(callSuper = false)
@Getter
public class GetHomePreferencePoolBuddyEventCommand extends SelfValidating<GetHomePreferencePoolBuddyEventCommand> {

	private final LocalDateTime eventStartDate;

	@Builder
	public GetHomePreferencePoolBuddyEventCommand(LocalDateTime eventStartDate) {

		if (eventStartDate.isAfter(LocalDateTime.now()) == false)
			eventStartDate = LocalDateTime.now();

		this.eventStartDate = eventStartDate;

		this.validateSelf();
	}
}
