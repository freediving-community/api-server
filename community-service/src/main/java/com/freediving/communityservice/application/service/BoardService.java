package com.freediving.communityservice.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.freediving.communityservice.adapter.out.persistence.board.BoardJpaEntity;
import com.freediving.communityservice.adapter.out.persistence.board.BoardPersistenceMapper;
import com.freediving.communityservice.application.port.in.BoardReadCommand;
import com.freediving.communityservice.application.port.in.BoardUseCase;
import com.freediving.communityservice.application.port.in.BoardWriteCommand;
import com.freediving.communityservice.application.port.out.BoardWritePort;
import com.freediving.communityservice.domain.Board;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService implements BoardUseCase {

	private final BoardWritePort boardWritePort;
	private final BoardPersistenceMapper boardMapper;

	@Override
	public Board createBoard(BoardWriteCommand command) {
		//TODO 유효성 검사 : 이미 존재
		BoardJpaEntity savedBoardEntity = boardWritePort.makeBoard(command);
		Board board = boardMapper.mapToDomain(savedBoardEntity);
		// board.doBusinessLogic();
		return board;
	}

	@Override
	public List<Board> readBoardList(BoardReadCommand command) {
		return null;
	}
}
