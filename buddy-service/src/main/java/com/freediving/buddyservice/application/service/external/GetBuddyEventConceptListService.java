package com.freediving.buddyservice.application.service.external;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.freediving.buddyservice.adapter.out.persistence.concept.BuddyEventConceptJpaEntity;
import com.freediving.buddyservice.application.port.in.service.GetBuddyEventConceptListUseCase;
import com.freediving.buddyservice.application.port.out.service.BuddyEventConceptListResponse;
import com.freediving.buddyservice.application.port.out.service.GetBuddyEventConceptListPort;
import com.freediving.common.config.annotation.UseCase;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional
public class GetBuddyEventConceptListService implements GetBuddyEventConceptListUseCase {

	private final GetBuddyEventConceptListPort getBuddyEventConceptListPort;

	@Override
	public BuddyEventConceptListResponse getEventConcepts() {

		List<BuddyEventConceptJpaEntity> eventConceptList = getBuddyEventConceptListPort.getEventConceptList();

		BuddyEventConceptListResponse result = BuddyEventConceptListResponse.builder().build();

		for (BuddyEventConceptJpaEntity entity : eventConceptList)
			result.add(BuddyEventConceptListResponse.EventConcept.builder().conceptId(entity.getConceptId()).conceptName(
				entity.getConceptName()).build());

		return result;
	}
}
