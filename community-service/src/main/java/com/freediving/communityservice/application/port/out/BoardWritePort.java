package com.freediving.communityservice.application.port.out;

import com.freediving.communityservice.adapter.out.persistence.board.BoardJpaEntity;
import com.freediving.communityservice.application.port.in.BoardWriteCommand;

public interface BoardWritePort {
	BoardJpaEntity makeBoard(BoardWriteCommand boardWriteCommand);
}
