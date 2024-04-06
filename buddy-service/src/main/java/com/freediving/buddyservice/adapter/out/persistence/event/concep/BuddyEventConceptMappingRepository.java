package com.freediving.buddyservice.adapter.out.persistence.event.concep;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BuddyEventConceptMappingRepository
	extends JpaRepository<BuddyEventConceptMappingJpaEntity, BuddyEventConceptMappingId> {
}
