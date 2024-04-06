package com.freediving.buddyservice.adapter.out.persistence.event.concept;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BuddyEventConceptMappingRepository
	extends JpaRepository<BuddyEventConceptMappingJpaEntity, BuddyEventConceptMappingId> {
}
