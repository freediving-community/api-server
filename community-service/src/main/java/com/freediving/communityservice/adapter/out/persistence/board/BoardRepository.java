package com.freediving.communityservice.adapter.out.persistence.board;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardJpaEntity, Long> {
}
