package com.freediving.communityservice.application.service;

import org.springframework.stereotype.Service;

import com.freediving.communityservice.adapter.out.persistence.BoardJpaEntity;
import com.freediving.communityservice.adapter.out.persistence.BoardPersistenceMapper;
import com.freediving.communityservice.application.port.in.MakeBoardCommand;
import com.freediving.communityservice.application.port.in.query.MakeBoardUseCase;
import com.freediving.communityservice.application.port.out.MakeBoardPort;
import com.freediving.communityservice.domain.Board;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MakeBoardService implements MakeBoardUseCase {

	private final MakeBoardPort makeBoardPort;
	private final BoardPersistenceMapper boardMapper;

	@Override
	public Board createBoard(MakeBoardCommand command) {

		//TODO 유효성 검사 : 이미 존재

		BoardJpaEntity savedBoardEntity = makeBoardPort.makeBoard(command);
		Board board = boardMapper.mapToDomain(savedBoardEntity);
		// board.doBusinessLogic();
		return board;
	}
}
