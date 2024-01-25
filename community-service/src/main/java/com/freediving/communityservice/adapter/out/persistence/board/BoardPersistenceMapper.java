package com.freediving.communityservice.adapter.out.persistence.board;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.freediving.communityservice.domain.Board;

@Component
public class BoardPersistenceMapper {
	public Board mapToDomain(BoardJpaEntity boardJpaEntity) {
		return Board.of(
			boardJpaEntity.getId(),
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

	public Optional<Board> mapToDomain(Optional<BoardJpaEntity> optionalBoard) {
		if (optionalBoard.isEmpty()) {
			return Optional.empty();
		}
		BoardJpaEntity boardJpaEntity = optionalBoard.get();
		return Optional.of(
			Board.of(
				boardJpaEntity.getId(),
				boardJpaEntity.getBoardType(),
				boardJpaEntity.getBoardName(),
				boardJpaEntity.getDescription(),
				boardJpaEntity.getSortOrder(),
				boardJpaEntity.isEnabled(),
				boardJpaEntity.getCreatedAt(),
				boardJpaEntity.getModifiedAt(),
				boardJpaEntity.getCreatedBy(),
				boardJpaEntity.getModifiedBy()
			));
	}
}
