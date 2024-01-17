package com.freediving.communityservice.adapter.out.dto.board;

import java.util.Collections;
import java.util.List;

import com.freediving.communityservice.domain.Board;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardResponse {
	//TODO 응답 규격 및 transient 등 추후 설정
	private String status;

	private List<Board> board;

	public static BoardResponse of(String status, List<Board> boards) {
		return new BoardResponse(status, boards);
	}

	public static BoardResponse of(String status, Board board) {
		return new BoardResponse(status, Collections.singletonList(board));
	}
}
