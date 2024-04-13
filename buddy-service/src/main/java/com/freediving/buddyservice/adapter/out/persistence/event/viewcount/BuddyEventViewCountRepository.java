package com.freediving.buddyservice.adapter.out.persistence.event.viewcount;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BuddyEventViewCountRepository extends JpaRepository<BuddyEventViewCountJpaEntity, Long> {
}
