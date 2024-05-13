package com.freediving.buddyservice.adapter.out.persistence.event;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.freediving.buddyservice.adapter.out.persistence.event.concept.BuddyEventConceptMappingJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.divingpool.BuddyEventDivingPoolMappingJpaEntity;
import com.freediving.buddyservice.domain.command.CreatedBuddyEventResponse;
import com.freediving.buddyservice.domain.enumeration.BuddyEventConcept;
import com.freediving.common.enumerate.DivingPool;

@Component
public class BuddyEventResponseMapper {

	/**
	 * 버디 일정 이벤트 JPA 객체를 버디 일정 이벤트 도메인객체로 변환한다.
	 *
	 * @param buddyEventJpaEntity the buddy event jpa entity
	 * @return the created buddy event
	 */
	public CreatedBuddyEventResponse mapToDomainEntity(BuddyEventJpaEntity buddyEventJpaEntity) {

		Set<DivingPool> divingPools = new HashSet<>();
		Set<BuddyEventConcept> concepts = new HashSet<>();

		if (buddyEventJpaEntity.getBuddyEventDivingPoolMappingJpaEntity() != null &&
			buddyEventJpaEntity.getBuddyEventDivingPoolMappingJpaEntity().isEmpty() == false)
			for (BuddyEventDivingPoolMappingJpaEntity row : buddyEventJpaEntity.getBuddyEventDivingPoolMappingJpaEntity())
				divingPools.add(row.getDivingPoolId());

		if (buddyEventJpaEntity.getEventConcepts() != null &&
			buddyEventJpaEntity.getEventConcepts().isEmpty() == false)
			for (BuddyEventConceptMappingJpaEntity row : buddyEventJpaEntity.getEventConcepts())
				concepts.add(row.getConceptId());

		return CreatedBuddyEventResponse.builder()
			.eventId(buddyEventJpaEntity.getEventId())
			.userId(buddyEventJpaEntity.getUserId())
			.eventStartDate(buddyEventJpaEntity.getEventStartDate())
			.eventEndDate(buddyEventJpaEntity.getEventEndDate())
			.participantCount(buddyEventJpaEntity.getParticipantCount())
			.status(buddyEventJpaEntity.getStatus())
			.genderType(buddyEventJpaEntity.getGenderType())
			.carShareYn(buddyEventJpaEntity.getCarShareYn())
			.freedivingLevel(buddyEventJpaEntity.getFreedivingLevel())
			.comment(buddyEventJpaEntity.getComment())
			.kakaoRoomCode(buddyEventJpaEntity.getKakaoRoomCode())
			.divingPools(divingPools)
			.freedivingLevel(buddyEventJpaEntity.getFreedivingLevel())
			.buddyEventConcepts(concepts)
			.updatedDate(buddyEventJpaEntity.getUpdatedDate())
			.createdDate(buddyEventJpaEntity.getCreatedDate())
			.build();
	}

}
