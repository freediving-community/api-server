package com.freediving.buddyservice.adapter.out.persistence.event;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.freediving.buddyservice.common.enumeration.BuddyEventConcept;
import com.freediving.buddyservice.domain.CreatedBuddyEventResponse;
import com.freediving.common.enumerate.DivingPool;

@Component
public class CreatedBuddyEventResponseMapper {

	/**
	 * 버디 일정 이벤트 JPA 객체를 버디 일정 이벤트 도메인객체로 변환한다.
	 *
	 * @param buddyEventJpaEntity the buddy event jpa entity
	 * @return the created buddy event
	 */
	public CreatedBuddyEventResponse mapToDomainEntity(BuddyEventJpaEntity buddyEventJpaEntity) {

		Set<DivingPool> divingPools = new HashSet<>();

		if (buddyEventJpaEntity.getBuddyEventDivingPoolMappingJpaEntity().isEmpty() == false)
			for (BuddyEventDivingPoolMappingJpaEntity row : buddyEventJpaEntity.getBuddyEventDivingPoolMappingJpaEntity())
				divingPools.add(row.getDivingPoolId());

		Set<BuddyEventConcept> buddyEventConcepts = new HashSet<>();
		if (buddyEventJpaEntity.getEventConcepts().isEmpty() == false)
			for (BuddyEventConceptMappingJpaEntity row : buddyEventJpaEntity.getEventConcepts())
				buddyEventConcepts.add(row.getConceptId());

		return CreatedBuddyEventResponse.builder()
			.eventId(buddyEventJpaEntity.getEventId())
			.userId(buddyEventJpaEntity.getUserId())
			.eventStartDate(buddyEventJpaEntity.getEventStartDate())
			.eventEndDate(buddyEventJpaEntity.getEventEndDate())
			.participantCount(buddyEventJpaEntity.getParticipantCount())
			.status(buddyEventJpaEntity.getStatus())
			.carShareYn(buddyEventJpaEntity.getCarShareYn())
			.buddyEventConcepts(buddyEventConcepts)
			.comment(buddyEventJpaEntity.getComment())
			.kakaoRoomCode(buddyEventJpaEntity.getKakaoRoomCode())
			.divingPools(divingPools)
			.freedivingLevel(buddyEventJpaEntity.getBuddyEventConditionsJpaEntity().getFreedivingLevel())
			.updatedDate(buddyEventJpaEntity.getUpdatedDate())
			.createdDate(buddyEventJpaEntity.getCreatedDate())
			.build();
	}

}
