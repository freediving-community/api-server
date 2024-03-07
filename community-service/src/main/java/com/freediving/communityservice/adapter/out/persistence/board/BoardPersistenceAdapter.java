package com.freediving.communityservice.adapter.out.persistence.board;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.communityservice.application.port.in.BoardReadCommand;
import com.freediving.communityservice.application.port.in.BoardWriteCommand;
import com.freediving.communityservice.application.port.out.BoardReadPort;
import com.freediving.communityservice.application.port.out.BoardWritePort;
import com.freediving.communityservice.domain.Board;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class BoardPersistenceAdapter implements BoardWritePort, BoardReadPort {

	private final BoardRepository boardRepository;
	private final JPAQueryFactory jpaQueryFactory;
	private final BoardPersistenceMapper boardMapper;

	@Override
	public Board makeBoard(BoardWriteCommand boardWriteCommand) {
		BoardJpaEntity boardJpaEntity = boardRepository.save(
			BoardJpaEntity.of(
				boardWriteCommand.getBoardType(),
				boardWriteCommand.getBoardName(),
				boardWriteCommand.getDescription(),
				boardWriteCommand.getSortOrder()
			)
		);
		return boardMapper.mapToDomain(boardJpaEntity);
	}

	public Board findById(Long boardId) {
		Optional<BoardJpaEntity> boardJpaEntity = boardRepository.findById(boardId);
		return boardMapper.mapToDomain(
			boardJpaEntity.orElseThrow(() -> new IllegalArgumentException("해당하는 게시판이 없습니다."))
		);
	}

	public Optional<Board> findByBoardName(String boardName) {
		Optional<BoardJpaEntity> boardJpaEntity = boardRepository.findByBoardName(boardName);
		return boardMapper.mapToDomain(boardJpaEntity);
	}

	@Override
	public List<Board> findAllBoards(BoardReadCommand command) {
		QBoardJpaEntity board = QBoardJpaEntity.boardJpaEntity;
		BooleanExpression booleanExpression = board.enabled.isTrue();
		List<BoardJpaEntity> boardJpaEntityList = jpaQueryFactory
			.selectFrom(board)
			.where(booleanExpression)
			.fetch();
		return boardJpaEntityList.stream()
			.map(boardMapper::mapToDomain)
			.collect(Collectors.toList());
	}
}
