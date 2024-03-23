package com.freediving.buddyservice.application.port.out.service;

import java.util.List;

import com.freediving.buddyservice.adapter.out.persistence.concept.BuddyEventConceptJpaEntity;

public interface GetBuddyEventConceptListPort {

	List<BuddyEventConceptJpaEntity> getEventConceptList();
}
