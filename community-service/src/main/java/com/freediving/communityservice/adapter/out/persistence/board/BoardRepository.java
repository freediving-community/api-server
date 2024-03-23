package com.freediving.communityservice.adapter.out.persistence.board;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;

public interface BoardRepository extends JpaRepository<BoardJpaEntity, Long>
	, QuerydslPredicateExecutor<BoardJpaEntity> {
	Optional<BoardJpaEntity> findByBoardType(BoardType boardType);
}
