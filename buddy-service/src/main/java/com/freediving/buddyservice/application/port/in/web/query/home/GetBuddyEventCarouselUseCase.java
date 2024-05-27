package com.freediving.buddyservice.application.port.in.web.query.home;

import com.freediving.buddyservice.domain.query.QueryComponentListResponse;

public interface GetBuddyEventCarouselUseCase {
	QueryComponentListResponse getHomeWeekly(Long userId, GetBuddyEventCarouselCommand command);

}
