package com.freediving.communityservice.application.port.in;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
import com.freediving.communityservice.domain.Board;

@Component
public interface BoardUseCase {
	Board createBoard(BoardWriteCommand command);

	List<Board> readBoardList(BoardReadCommand command);

	Optional<Board> readBoard(BoardType boardType);
}
