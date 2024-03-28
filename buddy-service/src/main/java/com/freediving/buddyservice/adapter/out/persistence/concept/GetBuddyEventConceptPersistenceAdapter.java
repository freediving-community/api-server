package com.freediving.buddyservice.adapter.out.persistence.concept;

import java.util.List;

import com.freediving.buddyservice.application.port.out.service.GetBuddyEventConceptListPort;
import com.freediving.common.config.annotation.PersistenceAdapter;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class GetBuddyEventConceptPersistenceAdapter implements GetBuddyEventConceptListPort {

	private final BuddyEventConceptRepository buddyEventConceptRepository;

	public List<BuddyEventConceptJpaEntity> getEventConceptList() {

		return buddyEventConceptRepository.findByEnabledIsTrueOrderByDisplayOrderAsc();
	}

}
