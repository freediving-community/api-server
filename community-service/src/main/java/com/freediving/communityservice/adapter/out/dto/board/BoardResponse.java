package com.freediving.communityservice.adapter.out.dto.board;

import com.freediving.communityservice.domain.Board;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardResponse {

	private String status;

	private Board board;

	public static BoardResponse of(String status, Board board) {
		return new BoardResponse(status, board);
	}
}
