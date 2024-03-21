package com.freediving.buddyservice.application.service.external;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.freediving.buddyservice.adapter.out.persistence.concept.EventConceptJpaEntity;
import com.freediving.buddyservice.application.port.in.service.GetEventConceptListUseCase;
import com.freediving.buddyservice.application.port.out.service.EventConceptListResponse;
import com.freediving.buddyservice.application.port.out.service.GetEventConceptListPort;
import com.freediving.common.config.annotation.UseCase;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional
public class GetEventConceptListService implements GetEventConceptListUseCase {

	private final GetEventConceptListPort getEventConceptListPort;

	@Override
	public EventConceptListResponse getEventConcepts() {

		List<EventConceptJpaEntity> eventConceptList = getEventConceptListPort.getEventConceptList();

		EventConceptListResponse result = EventConceptListResponse.builder().build();

		for (EventConceptJpaEntity entity : eventConceptList)
			result.add(EventConceptListResponse.EventConcept.builder().conceptId(entity.getConceptId()).conceptName(
				entity.getConceptName()).build());

		return result;
	}
}
