package com.freediving.buddyservice.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.freediving.buddyservice.domain.CreatedBuddyEvent;

@Component
public class CreatedBuddyEventMapper {

	/**
	 * 버디 일정 이벤트 JPA 객체를 버디 일정 이벤트 도메인객체로 변환한다.
	 *
	 * @param buddyEventsJpaEntity the buddy events jpa entity
	 * @return the created buddy event
	 */
	public CreatedBuddyEvent mapToDomainEntity(BuddyEventsJpaEntity buddyEventsJpaEntity) {
		return CreatedBuddyEvent.builder()
			.eventId(buddyEventsJpaEntity.getEventId())
			.userId(buddyEventsJpaEntity.getUserId())
			.eventStartDate(buddyEventsJpaEntity.getEventStartDate())
			.eventEndDate(buddyEventsJpaEntity.getEventEndDate())
			.participantCount(buddyEventsJpaEntity.getParticipantCount())
			.status(buddyEventsJpaEntity.getStatus())
			.carShareYn(buddyEventsJpaEntity.getCarShareYn())
			.eventConcepts(buddyEventsJpaEntity.getEventConcepts())
			.comment(buddyEventsJpaEntity.getComment())
			.build();
	}

}
