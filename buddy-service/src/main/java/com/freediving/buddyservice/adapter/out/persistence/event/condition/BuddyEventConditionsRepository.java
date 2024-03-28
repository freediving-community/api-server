package com.freediving.buddyservice.adapter.out.persistence.event.condition;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BuddyEventConditionsRepository extends JpaRepository<BuddyEventConditionsJpaEntity, Long> {
}
