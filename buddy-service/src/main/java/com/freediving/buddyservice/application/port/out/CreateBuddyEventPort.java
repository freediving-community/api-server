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

	/**
	 * 생성하려는 버디 이벤트 시간에 이미 생성된 버디 이벤트가 존재하는지 유효성 체크를 한다.
	 *
	 * @return true - 겹치는 이벤트가 없어 유효하다. false - 겹치는 이벤트가 존재하여 유요하지 않는다.
	 */
	Boolean isValidBuddyEventOverlap();

}
