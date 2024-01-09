package com.freediving.communityservice.application.port.in;

import java.util.List;

import org.springframework.stereotype.Component;

import com.freediving.communityservice.domain.Board;

@Component
public interface BoardUseCase {
	Board createBoard(BoardWriteCommand command);

	List<Board> readBoardList(BoardReadCommand command);
}
