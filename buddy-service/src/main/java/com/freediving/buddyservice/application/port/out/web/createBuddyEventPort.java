package com.freediving.buddyservice.application.port.out.web;

import com.freediving.buddyservice.adapter.out.persistence.event.BuddyEventJpaEntity;
import com.freediving.buddyservice.domain.command.CreatedBuddyEventResponse;

public interface createBuddyEventPort {

	/**
	 * 버디 일정 이벤트 생성의 port Interface
	 * Interface 구현 필요.
	 *
	 * @param createdBuddyEventResponse persistence 로 전달될 버디 일정 이벤트 정보.
	 * @return 생성된 버디 일정 이벤트 JPA 객체
	 */
	BuddyEventJpaEntity createBuddyEvent(
		CreatedBuddyEventResponse createdBuddyEventResponse
	);

}
