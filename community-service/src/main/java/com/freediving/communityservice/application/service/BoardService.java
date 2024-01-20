package com.freediving.communityservice.application.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.freediving.communityservice.adapter.out.persistence.board.BoardJpaEntity;
import com.freediving.communityservice.adapter.out.persistence.board.BoardPersistenceMapper;
import com.freediving.communityservice.application.port.in.BoardReadCommand;
import com.freediving.communityservice.application.port.in.BoardUseCase;
import com.freediving.communityservice.application.port.in.BoardWriteCommand;
import com.freediving.communityservice.application.port.out.BoardReadPort;
import com.freediving.communityservice.application.port.out.BoardWritePort;
import com.freediving.communityservice.domain.Board;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService implements BoardUseCase {

	private final BoardWritePort boardWritePort;
	private final BoardReadPort boardReadPort;
	private final BoardPersistenceMapper boardMapper;

	@Override
	public Board createBoard(BoardWriteCommand command) {
		boardReadPort.findByBoardName(command.getBoardName())
			.ifPresent(board -> {
				throw new IllegalArgumentException("이미 동일한 이름의 게시판이 존재합니다.");
			});
		BoardJpaEntity savedBoardEntity = boardWritePort.makeBoard(command);
		return boardMapper.mapToDomain(savedBoardEntity);
	}

	@Override
	public Board readBoard(Long boardId) {
		Optional<BoardJpaEntity> boardJpaEntity = boardReadPort.findById(boardId);
		return boardMapper.mapToDomain(boardJpaEntity
			.orElseThrow(() -> new IllegalArgumentException("해당하는 게시판이 없습니다.")));
	}

	@Override
	public List<Board> readBoardList(BoardReadCommand command) {
		List<BoardJpaEntity> boardJpaEntityList = boardReadPort.findAllBoards(command);
		return boardJpaEntityList.stream()
			.map(boardMapper::mapToDomain)
			.collect(Collectors.toList());
	}
}