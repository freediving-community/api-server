package com.freediving.buddyservice.adapter.out.persistence.event;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.freediving.buddyservice.common.enumeration.EventConcept;
import com.freediving.buddyservice.domain.CreatedBuddyEvent;
import com.freediving.common.enumerate.DivingPool;

@Component
public class CreatedBuddyEventMapper {

	/**
	 * 버디 일정 이벤트 JPA 객체를 버디 일정 이벤트 도메인객체로 변환한다.
	 *
	 * @param buddyEventsJpaEntity the buddy events jpa entity
	 * @return the created buddy event
	 */
	public CreatedBuddyEvent mapToDomainEntity(BuddyEventsJpaEntity buddyEventsJpaEntity) {

		Set<DivingPool> divingPools = new HashSet<>();

		if (buddyEventsJpaEntity.getEventsDivingPoolMapping().isEmpty() == false)
			for (EventsDivingPoolMapping row : buddyEventsJpaEntity.getEventsDivingPoolMapping())
				divingPools.add(row.getDivingPoolId());

		Set<EventConcept> eventConcepts = new HashSet<>();
		if (buddyEventsJpaEntity.getEventConcepts().isEmpty() == false)
			for (EventsConceptMapping row : buddyEventsJpaEntity.getEventConcepts())
				eventConcepts.add(row.getConceptId());

		return CreatedBuddyEvent.builder()
			.eventId(buddyEventsJpaEntity.getEventId())
			.userId(buddyEventsJpaEntity.getUserId())
			.eventStartDate(buddyEventsJpaEntity.getEventStartDate())
			.eventEndDate(buddyEventsJpaEntity.getEventEndDate())
			.participantCount(buddyEventsJpaEntity.getParticipantCount())
			.status(buddyEventsJpaEntity.getStatus())
			.carShareYn(buddyEventsJpaEntity.getCarShareYn())
			.eventConcepts(eventConcepts)
			.comment(buddyEventsJpaEntity.getComment())
			.kakaoRoomCode(buddyEventsJpaEntity.getKakaoRoomCode())
			.divingPools(divingPools)
			.freedivingLevel(buddyEventsJpaEntity.getBuddyEventConditions().getFreedivingLevel())
			.updatedDate(buddyEventsJpaEntity.getUpdatedDate())
			.createdDate(buddyEventsJpaEntity.getCreatedDate())
			.build();
	}

}
