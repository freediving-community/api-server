package com.freediving.buddyservice.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuddyEventsRepository extends JpaRepository<BuddyEventsJpaEntity, Long> {

}
