package com.freediving.buddyservice.application.port.in.web.query;

import com.freediving.buddyservice.domain.query.QueryBuddyEventDetailResponse;

public interface BuddyEventDetailUseCase {
	QueryBuddyEventDetailResponse getBuddyEventDetail(BuddyEventDetailCommand command);

}
