package com.freediving.communityservice.application.port.out;

import com.freediving.communityservice.application.port.in.BoardWriteCommand;
import com.freediving.communityservice.domain.Board;

public interface BoardWritePort {
	Board makeBoard(BoardWriteCommand boardWriteCommand);
}
