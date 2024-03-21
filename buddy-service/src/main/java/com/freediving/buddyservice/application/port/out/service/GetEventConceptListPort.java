package com.freediving.buddyservice.application.port.out.service;

import java.util.List;

import com.freediving.buddyservice.adapter.out.persistence.concept.EventConceptJpaEntity;

public interface GetEventConceptListPort {

	List<EventConceptJpaEntity> getEventConceptList();
}
