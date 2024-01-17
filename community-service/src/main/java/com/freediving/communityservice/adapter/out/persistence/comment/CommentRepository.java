package com.freediving.communityservice.adapter.out.persistence.comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentJpaEntity, Long> {
}
