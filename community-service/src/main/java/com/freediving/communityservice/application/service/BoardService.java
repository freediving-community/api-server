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
		//TODO 유효성 검사 : 이미 존재
		BoardJpaEntity savedBoardEntity = boardWritePort.makeBoard(command);
		// board.doBusinessLogic();
		return boardMapper.mapToDomain(savedBoardEntity);
	}

	@Override
	public Board readBoard(Long boardId) {
		Optional<BoardJpaEntity> boardJpaEntity = boardReadPort.findBoard(boardId);
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
