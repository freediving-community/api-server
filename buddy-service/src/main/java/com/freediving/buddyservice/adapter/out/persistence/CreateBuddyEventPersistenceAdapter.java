package com.freediving.buddyservice.adapter.out.persistence;

import java.time.LocalDateTime;
import java.util.List;

import com.freediving.buddyservice.application.port.out.CreateBuddyEventPort;
import com.freediving.buddyservice.domain.CreatedBuddyEvent;
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
public class CreateBuddyEventPersistenceAdapter implements CreateBuddyEventPort {

	private final BuddyEventsRepository buddyEventsRepository;

	@Override
	public BuddyEventsJpaEntity createBuddyEvent(CreatedBuddyEvent createdBuddyEvent) {

		return buddyEventsRepository.save(
			BuddyEventsJpaEntity.builder()
				.userId(createdBuddyEvent.getUserId())
				.eventStartDate(createdBuddyEvent.getEventStartDate())
				.eventEndDate(createdBuddyEvent.getEventEndDate())
				.participantCount(createdBuddyEvent.getParticipantCount())
				.eventConcepts(createdBuddyEvent.getEventConcepts())
				.carShareYn(createdBuddyEvent.getCarShareYn())
				.status(createdBuddyEvent.getStatus())
				.kakaoRoomCode(createdBuddyEvent.getKakaoRoomCode())
				.comment(createdBuddyEvent.getComment())
				.build()
		);

	}

	@Override
	public Boolean isValidBuddyEventOverlap(Long userId, LocalDateTime eventStartDate, LocalDateTime eventEndTime,
		List<String> statuses) {
		if (buddyEventsRepository.existsBuddyEventByEventTime(userId, eventStartDate
			, eventEndTime, statuses) == true)
			return false;

		return true;

	}

}
