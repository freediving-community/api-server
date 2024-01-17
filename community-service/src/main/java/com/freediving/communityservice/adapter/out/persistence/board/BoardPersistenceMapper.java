package com.freediving.communityservice.adapter.out.persistence.board;

import org.springframework.stereotype.Component;

import com.freediving.communityservice.domain.Board;

@Component
public class BoardPersistenceMapper {
	public Board mapToDomain(BoardJpaEntity boardJpaEntity) {
		return Board.of(boardJpaEntity.getId(),
			boardJpaEntity.getBoardType(),
			boardJpaEntity.getBoardName(),
			boardJpaEntity.getDescription(),
			boardJpaEntity.getSortOrder(),
			boardJpaEntity.isEnabled(),
			boardJpaEntity.getCreatedAt(),
			boardJpaEntity.getModifiedAt(),
			boardJpaEntity.getCreatedBy(),
			boardJpaEntity.getModifiedBy()
		);
	}
}
