package com.freediving.communityservice.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Board {
	private final Long id;

	private final String boardType;

	private final String boardName;

	private final String description;

	private final int sortOrder;

	private final boolean enabled;

	private final LocalDateTime createdAt;

	private final LocalDateTime modifiedAt;

	private final Long createdBy;

	private final Long modifiedBy;

	public static Board of(Long id, String boardType, String boardName, String description, int sortOrder,
		boolean enabled, LocalDateTime createdAt, LocalDateTime modifiedAt, Long createdBy, Long modifiedBy) {

		return new Board(id, boardType, boardName, description, sortOrder, enabled, createdAt, modifiedAt, createdBy,
			modifiedBy);
	}
}
