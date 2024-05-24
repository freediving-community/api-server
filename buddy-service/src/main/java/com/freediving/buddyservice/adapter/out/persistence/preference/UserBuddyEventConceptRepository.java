package com.freediving.buddyservice.adapter.out.persistence.preference;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBuddyEventConceptRepository
	extends JpaRepository<UserBuddyEventConceptEntity, UserBuddyEventConceptId> {
	void deleteByUserId(Long userId);
}