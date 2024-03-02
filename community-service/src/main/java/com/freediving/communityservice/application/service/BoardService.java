package com.freediving.communityservice.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
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

	@Override
	public Board createBoard(BoardWriteCommand command) {
		boardReadPort.findByBoardType(command.getBoardType())
			.ifPresent(board -> {
				throw new IllegalArgumentException("이미 동일한 이름(유형)의 게시판이 존재합니다.");
			});
		return boardWritePort.makeBoard(command);
	}

	@Override
	public Optional<Board> readBoard(BoardType boardType) {
		return boardReadPort.findByBoardType(boardType);
	}

	@Override
	public List<Board> readBoardList(BoardReadCommand command) {
		return boardReadPort.findAllBoards(command);
	}
}
