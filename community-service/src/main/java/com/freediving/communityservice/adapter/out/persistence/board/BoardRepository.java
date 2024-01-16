package com.freediving.communityservice.adapter.out.persistence.board;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BoardRepository extends JpaRepository<BoardJpaEntity, Long>
	, QuerydslPredicateExecutor<BoardJpaEntity> {
	Optional<BoardJpaEntity> findByBoardName(String boardName);
}
