package com.freediving.buddyservice.adapter.out.persistence.event.likecount;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freediving.buddyservice.adapter.out.persistence.event.BuddyEventJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.join.BuddyEventJoinRequestId;

public interface BuddyEventLikeMappingRepository
	extends JpaRepository<BuddyEventLikeMappingJpaEntity, BuddyEventJoinRequestId> {

	BuddyEventLikeMappingJpaEntity findByBuddyEventAndUserIdAndIsDeletedIsFalse(BuddyEventJpaEntity buddyEventJpaEntity,
		Long userId);

}
