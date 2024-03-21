package com.freediving.buddyservice.application.service.external;

import org.springframework.transaction.annotation.Transactional;

import com.freediving.buddyservice.application.port.in.service.GetEventConceptsUseCase;
import com.freediving.buddyservice.domain.EventConceptsResponse;
import com.freediving.common.config.annotation.UseCase;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional
public class GetEventConceptsService implements GetEventConceptsUseCase {

	@Override
	public EventConceptsResponse getEventConcepts() {

		return null;
	}
}
