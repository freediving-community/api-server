package com.freediving.buddyservice.adapter.out.persistence.concept;

import java.util.List;

import com.freediving.buddyservice.application.port.out.service.GetEventConceptListPort;
import com.freediving.common.config.annotation.PersistenceAdapter;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class GetEventConceptPersistenceAdapter implements GetEventConceptListPort {

	private final BuddyEventConceptRepository buddyEventConceptRepository;

	public List<EventConceptJpaEntity> getEventConceptList() {

		return buddyEventConceptRepository.findByEnabledIsTrueOrderByDisplayOrderAsc();
	}

}
