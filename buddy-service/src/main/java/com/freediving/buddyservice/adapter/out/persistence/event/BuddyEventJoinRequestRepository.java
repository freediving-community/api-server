package com.freediving.buddyservice.adapter.out.persistence.event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BuddyEventJoinRequestRepository
	extends JpaRepository<BuddyEventJoinRequestJpaEntity, BuddyEventJoinRequestId> {
}
