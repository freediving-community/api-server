package com.freediving.buddyservice.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.freediving.buddyservice.application.port.out.CreateBuddyEventPort;
import com.freediving.buddyservice.domain.CreatedBuddyEvent;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CreateBuddyEventPersistenceAdapter implements CreateBuddyEventPort {

	private final BuddyEventsRepository buddyEventsRepository;

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
