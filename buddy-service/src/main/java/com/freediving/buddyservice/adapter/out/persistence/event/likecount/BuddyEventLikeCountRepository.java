package com.freediving.buddyservice.adapter.out.persistence.event.likecount;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BuddyEventLikeCountRepository extends JpaRepository<BuddyEventLikeCountJpaEntity, Long> {
}
