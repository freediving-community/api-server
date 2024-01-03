package com.freediving.communityservice.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.freediving.communityservice.application.port.in.MakeBoardCommand;
import com.freediving.communityservice.application.port.out.MakeBoardPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MakeBoardPersistenceAdapter implements MakeBoardPort {

	private final MakeBoardRepository boardRepository;

	@Override
	public BoardJpaEntity makeBoard(MakeBoardCommand makeBoardCommand) {
		return boardRepository.save(BoardJpaEntity.of(makeBoardCommand.getBoardType(), makeBoardCommand.getBoardName(),
			makeBoardCommand.getDescription(), makeBoardCommand.getSortOrder()));
	}
}
