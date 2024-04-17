package com.freediving.buddyservice.adapter.out.persistence.event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.freediving.buddyservice.application.port.out.web.ValidationBuddyEventPort;
import com.freediving.common.config.annotation.PersistenceAdapter;

import lombok.RequiredArgsConstructor;

/**
 * Persistence 와의 연결을 담당하는 Adapter
 * 이 객체는 Persistence 와의 명령 수행만 담당한다. 비즈니스 적인 로직은 불가.
 *
 * @author 준희조
 * @version 1.0.0
 * 작성일 2023-12-27
 **/
@RequiredArgsConstructor
@PersistenceAdapter
public class ValidationBuddyEventPersistenceAdapter implements ValidationBuddyEventPort {

	private final BuddyEventRepository buddyEventRepository;

	//todo 같은 시작,종료로 만들면 만들어짐.
	@Override
	public Boolean isValidBuddyEventOverlap(final Long userId, final LocalDateTime eventStartDate,
		final LocalDateTime eventEndTime,
		final List<String> statuses) {
		if (buddyEventRepository.existsBuddyEventByEventTime(userId, eventStartDate
			, eventEndTime, statuses) == true)
			return false;

		return true;

	}

	@Override
	public BuddyEventJpaEntity existBuddyEvent(final Long eventId) {
		Optional<BuddyEventJpaEntity> buddyEvent = buddyEventRepository.findById(eventId);

		if (buddyEvent.isPresent() == false)
			return null;

		return buddyEvent.get();
	}

}
