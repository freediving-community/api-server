package com.freediving.communityservice.application.port.out;

import java.util.List;
import java.util.Optional;

import com.freediving.communityservice.adapter.out.persistence.board.BoardJpaEntity;
import com.freediving.communityservice.application.port.in.BoardReadCommand;

public interface BoardReadPort {
	Optional<BoardJpaEntity> findById(Long boardId);

	Optional<BoardJpaEntity> findByBoardName(String boardName);

	List<BoardJpaEntity> findAllBoards(BoardReadCommand command);
}
