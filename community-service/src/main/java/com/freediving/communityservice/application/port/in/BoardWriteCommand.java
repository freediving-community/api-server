package com.freediving.communityservice.application.port.in;

import com.freediving.common.SelfValidating;
import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class BoardWriteCommand extends SelfValidating<BoardWriteCommand> {
	// TODO id 제거 및 자동 생성
	private final Long id;

	private final BoardType boardType;

	private final String boardName;

	private final String description;

	private final int sortOrder;

	public BoardWriteCommand(Long id, BoardType boardType, String boardName, String description, int sortOrder) {
		this.id = id;
		this.boardType = boardType;
		this.boardName = boardName;
		this.description = description;
		this.sortOrder = sortOrder;
		this.validateSelf();
	}
}

