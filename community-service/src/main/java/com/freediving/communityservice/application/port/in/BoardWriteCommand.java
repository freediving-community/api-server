package com.freediving.communityservice.application.port.in;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class BoardWriteCommand {
	private final Long id;

	private final String boardType;

	private final String boardName;

	private final String description;

	private final int sortOrder;

	public BoardWriteCommand(Long id, String boardType, String boardName, String description, int sortOrder) {
		this.id = id;
		this.boardType = boardType;
		this.boardName = boardName;
		this.description = description;
		this.sortOrder = sortOrder;
	}
}

