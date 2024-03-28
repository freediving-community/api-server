package com.freediving.buddyservice.adapter.out.persistence.event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BuddyEventConditionsRepository extends JpaRepository<BuddyEventConditionsJpaEntity, Long> {
}
