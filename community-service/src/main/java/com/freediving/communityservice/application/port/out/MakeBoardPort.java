package com.freediving.communityservice.application.port.out;

import com.freediving.communityservice.adapter.out.persistence.BoardJpaEntity;
import com.freediving.communityservice.application.port.in.MakeBoardCommand;

public interface MakeBoardPort {
	BoardJpaEntity makeBoard(MakeBoardCommand makeBoardCommand);
}
