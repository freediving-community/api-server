package com.freediving.buddyservice.application.port.in.web.query.listing;

import com.freediving.buddyservice.domain.query.QueryComponentListResponse;

public interface GetBuddyEventListingUseCase {
	QueryComponentListResponse getBuddyEventListing(Long userId,
		GetBuddyEventListingCommand command);

	QueryComponentListResponse getLikeBuddyEventListing(Long userId, int pageNumber, int offset);

}
