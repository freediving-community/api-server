package com.freediving.buddyservice.application.port.in;

import com.freediving.buddyservice.domain.CreatedBuddyEvent;

public interface CreateBuddyEventUseCase {

	// 버디 일정 이벤트를 생성하는 Use Case
	CreatedBuddyEvent createBuddyEvent(CreateBuddyEventCommand command);
}
