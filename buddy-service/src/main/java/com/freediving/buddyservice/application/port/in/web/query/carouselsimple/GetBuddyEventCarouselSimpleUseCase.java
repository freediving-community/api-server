package com.freediving.buddyservice.application.port.in.web.query.carouselsimple;

import com.freediving.buddyservice.domain.query.QueryComponentListWithoutPageResponse;

public interface GetBuddyEventCarouselSimpleUseCase {
	QueryComponentListWithoutPageResponse getBuddyEventCarouselSimple(GetBuddyEventCarouselSimpleCommand command);
}
