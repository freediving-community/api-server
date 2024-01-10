package com.freediving.communityservice.adapter.out.persistence.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BoardRepository extends JpaRepository<BoardJpaEntity, Long>
	, QuerydslPredicateExecutor<BoardJpaEntity> {
}
