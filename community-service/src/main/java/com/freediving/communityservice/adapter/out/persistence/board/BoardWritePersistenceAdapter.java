package com.freediving.communityservice.adapter.out.persistence.board;

import org.springframework.stereotype.Component;

import com.freediving.communityservice.application.port.in.BoardWriteCommand;
import com.freediving.communityservice.application.port.out.BoardWritePort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BoardWritePersistenceAdapter implements BoardWritePort {

	private final BoardRepository boardRepository;

	@Override
	public BoardJpaEntity makeBoard(BoardWriteCommand boardWriteCommand) {
		return boardRepository.save(
			BoardJpaEntity.of(boardWriteCommand.getBoardType(), boardWriteCommand.getBoardName(),
				boardWriteCommand.getDescription(), boardWriteCommand.getSortOrder()));
	}
}
