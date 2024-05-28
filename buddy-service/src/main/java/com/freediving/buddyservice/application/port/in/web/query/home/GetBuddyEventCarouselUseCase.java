package com.freediving.buddyservice.application.port.in.web.query.home;

import com.freediving.buddyservice.domain.query.QueryComponentListResponse;
import com.freediving.buddyservice.domain.query.QueryPreferencePoolCarouselResponse;

public interface GetBuddyEventCarouselUseCase {
	QueryComponentListResponse getHomeWeekly(Long userId, GetHomeWeeklyBuddyEventCommand command);

	QueryPreferencePoolCarouselResponse getHomePreferencePoolBuddyEvent(Long userId,
		GetHomePreferencePoolBuddyEventCommand command);

	QueryComponentListResponse getHomeActiveBuddyEvent(Long userId, GetHomeActiveBuddyEventCommand command);
}
