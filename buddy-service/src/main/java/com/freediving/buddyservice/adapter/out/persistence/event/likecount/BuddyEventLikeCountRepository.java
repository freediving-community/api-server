package com.freediving.buddyservice.adapter.out.persistence.event.likecount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuddyEventLikeCountRepository extends JpaRepository<BuddyEventLikeCountJpaEntity, Long> {
}
