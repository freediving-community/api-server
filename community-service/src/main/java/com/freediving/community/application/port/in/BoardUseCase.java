package com.freediving.community.application.port.in;

import com.freediving.community.application.domain.Board;

public interface BoardUseCase {
	Long createBoard(CreateBoardCommand command);
}
