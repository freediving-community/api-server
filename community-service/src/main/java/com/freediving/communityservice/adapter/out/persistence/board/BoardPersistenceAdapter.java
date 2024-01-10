package com.freediving.communityservice.adapter.out.persistence.board;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.freediving.communityservice.application.port.in.BoardReadCommand;
import com.freediving.communityservice.application.port.in.BoardWriteCommand;
import com.freediving.communityservice.application.port.out.BoardReadPort;
import com.freediving.communityservice.application.port.out.BoardWritePort;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BoardPersistenceAdapter implements BoardWritePort, BoardReadPort {

	private final BoardRepository boardRepository;
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public BoardJpaEntity makeBoard(BoardWriteCommand boardWriteCommand) {
		return boardRepository.save(
			BoardJpaEntity.of(boardWriteCommand.getBoardType(), boardWriteCommand.getBoardName(),
				boardWriteCommand.getDescription(), boardWriteCommand.getSortOrder()));
	}

	@Override
	public Optional<BoardJpaEntity> findBoard(Long boardId) {
		return boardRepository.findById(boardId);
	}

	@Override
	public List<BoardJpaEntity> findAllBoards(BoardReadCommand command) {
		QBoardJpaEntity boardJpaEntity = QBoardJpaEntity.boardJpaEntity;
		BooleanExpression booleanExpression = boardJpaEntity.enabled.isTrue();
		return jpaQueryFactory.selectFrom(boardJpaEntity)
			.where(booleanExpression)
			.fetch();
	}
}
