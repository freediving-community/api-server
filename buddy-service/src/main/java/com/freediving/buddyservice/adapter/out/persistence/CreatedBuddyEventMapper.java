package com.freediving.buddyservice.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.freediving.buddyservice.domain.CreatedBuddyEvent;

@Component
public class CreatedBuddyEventMapper {

	public CreatedBuddyEvent mapToDomainEntity(BuddyEventsJpaEntity buddyEventsJpaEntity) {
		return CreatedBuddyEvent.builder()
			.eventId(buddyEventsJpaEntity.getEventId())
			.userId(buddyEventsJpaEntity.getUserId())
			.eventStartDate(buddyEventsJpaEntity.getEventStartTime())
			.eventEndDate(buddyEventsJpaEntity.getEventEndTime())
			.participantCount(buddyEventsJpaEntity.getParticipantCount())
			.status(buddyEventsJpaEntity.getStatus())
			.carShareYn(buddyEventsJpaEntity.getCarShareYn())
			.eventConcepts(buddyEventsJpaEntity.getEventConcepts())
			.comment(buddyEventsJpaEntity.getComment())
			.build();
	}

}
