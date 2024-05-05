package com.freediving.buddyservice.application.port.in.web.query.listing;

import com.freediving.buddyservice.domain.query.QueryComponentListResponse;

public interface GetBuddyEventListingUseCase {
	QueryComponentListResponse getBuddyEventListing(GetBuddyEventListingCommand command);

}
