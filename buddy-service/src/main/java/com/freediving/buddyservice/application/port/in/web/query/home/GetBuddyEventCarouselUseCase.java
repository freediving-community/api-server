package com.freediving.buddyservice.application.port.in.web.query.home;

import com.freediving.buddyservice.domain.query.QueryComponentListResponse;
import com.freediving.buddyservice.domain.query.QueryPreferencePoolCarouselResponse;

public interface GetBuddyEventCarouselUseCase {
	QueryComponentListResponse getHomeWeekly(Long userId, GetHomeWeeklyBuddyEventCommand command);

	QueryPreferencePoolCarouselResponse getHomeRecommendPoolBuddyEvent(Long userId,
		GetHomeRecommendPoolBuddyEventCommand command);
}
