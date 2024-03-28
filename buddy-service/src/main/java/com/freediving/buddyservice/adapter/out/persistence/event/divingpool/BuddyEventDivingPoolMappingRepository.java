package com.freediving.buddyservice.adapter.out.persistence.event.divingpool;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BuddyEventDivingPoolMappingRepository
	extends JpaRepository<BuddyEventDivingPoolMappingJpaEntity, BuddyEventDivingPoolMappingId> {
}
