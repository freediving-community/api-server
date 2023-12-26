package com.freediving.buddyservice.application.port.out;

import com.freediving.buddyservice.adapter.out.persistence.BuddyEventsJpaEntity;
import com.freediving.buddyservice.domain.CreatedBuddyEvent;

public interface CreateBuddyEventPort {
	BuddyEventsJpaEntity createBuddyEvent(
		CreatedBuddyEvent createdBuddyEvent
	);

}
