package com.freediving.buddyservice.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.freediving.buddyservice.application.port.out.CreateBuddyEventPort;
import com.freediving.buddyservice.domain.CreatedBuddyEvent;

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
@Component
public class CreateBuddyEventPersistenceAdapter implements CreateBuddyEventPort {

	private final BuddyEventsRepository buddyEventsRepository;

	/**
	 * jpa 버디 일정 이벤트 생성 save
	 *
	 * @param createdBuddyEvent 서비스 비즈니스가 적용된 new 버디 일정 이벤트
	 * @return 생성된 버디 일정 이벤트 JPA 객체
	 */
	@Override
	public BuddyEventsJpaEntity createBuddyEvent(CreatedBuddyEvent createdBuddyEvent) {

		return buddyEventsRepository.save(
			BuddyEventsJpaEntity.builder()
				.userId(createdBuddyEvent.getUserId())
				.eventStartTime(createdBuddyEvent.getEventStartDate())
				.eventEndTime(createdBuddyEvent.getEventEndDate())
				.participantCount(createdBuddyEvent.getParticipantCount())
				.eventConcepts(createdBuddyEvent.getEventConcepts())
				.carShareYn(createdBuddyEvent.getCarShareYn())
				.status(createdBuddyEvent.getStatus())
				.comment(createdBuddyEvent.getComment())
				.build()
		);

	}
}
