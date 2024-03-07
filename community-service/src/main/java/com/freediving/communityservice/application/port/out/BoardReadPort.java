package com.freediving.communityservice.application.port.out;

import java.util.List;
import java.util.Optional;

import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
import com.freediving.communityservice.application.port.in.BoardReadCommand;
import com.freediving.communityservice.domain.Board;

public interface BoardReadPort {
	Board findById(Long boardId);

	Optional<Board> findByBoardType(BoardType boardType);

	List<Board> findAllBoards(BoardReadCommand command);
}
