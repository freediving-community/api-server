package com.freediving.buddyservice.application.port.out;

import com.freediving.buddyservice.adapter.out.persistence.BuddyEventsJpaEntity;
import com.freediving.buddyservice.domain.CreatedBuddyEvent;

public interface CreateBuddyEventPort {

	/**
	 * 버디 일정 이벤트 생성의 port Interface
	 * Interface 구현 필요.
	 *
	 * @param createdBuddyEvent persistence 로 전달될 버디 일정 이벤트 정보.
	 * @return 생성된 버디 일정 이벤트 JPA 객체
	 */
	BuddyEventsJpaEntity createBuddyEvent(
		CreatedBuddyEvent createdBuddyEvent
	);

}
