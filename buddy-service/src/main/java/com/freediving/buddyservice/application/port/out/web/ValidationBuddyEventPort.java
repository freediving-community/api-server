package com.freediving.buddyservice.application.port.out.web;

import java.time.LocalDateTime;
import java.util.List;

import com.freediving.buddyservice.adapter.out.persistence.event.BuddyEventJpaEntity;

public interface ValidationBuddyEventPort {
	/**
	 * 생성하려는 버디 이벤트 시간에 이미 생성된 버디 이벤트가 존재하는지 유효성 체크를 한다.
	 *
	 * @return true - 겹치는 이벤트가 없어 유효하다. false - 겹치는 이벤트가 존재하여 유요하지 않는다.
	 */
	Boolean isValidBuddyEventOverlap(final Long userId, final LocalDateTime eventStartDate,
		final LocalDateTime eventEndTime, final List<String> statuses);

	/**
	 * 버디 이벤트가 존재 하는지만 체크한다.
	 *
	 * @param eventId 존재하는지 체크하고자 하는 이벤트 식별 ID
	 * @return 존재한다면 JPA 객체, 존재 안하면 null 리턴
	 */

	BuddyEventJpaEntity existBuddyEvent(final Long eventId);
}
