package com.freediving.communityservice.application.port.in.query;

import org.springframework.stereotype.Component;

import com.freediving.communityservice.application.port.in.MakeBoardCommand;
import com.freediving.communityservice.domain.Board;

@Component
public interface MakeBoardUseCase {
	Board createBoard(MakeBoardCommand command);
}
