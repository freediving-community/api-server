package com.freediving.communityservice.adapter.out.persistence.userreact;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReactionRepository extends JpaRepository<UserReactionJpaEntity, UserReactionId> {
}
