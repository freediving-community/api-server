package com.freediving.communityservice.adapter.in.web.query;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.communityservice.adapter.out.dto.board.BoardResponse;
import com.freediving.communityservice.application.port.in.BoardReadCommand;
import com.freediving.communityservice.application.port.in.BoardUseCase;
import com.freediving.communityservice.domain.Board;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class BoardQueryController {

	private final BoardUseCase boardUseCase;

	@GetMapping("/boards/{boardId}")
	public ResponseEntity<BoardResponse> getBoardDetail(@PathVariable("boardId") Long boardId) {
		Board board = boardUseCase.readBoard(boardId);
		return ResponseEntity.ok(BoardResponse.of("success", board));
	}

	@GetMapping("/boards")
	public ResponseEntity<BoardResponse> getBoards() {
		boolean isAdmin = false;
		// boolean isAdmin = checkAdmin(principal); //TODO 관리자인지 확인하는 로직
		BoardReadCommand boardReadCommand = BoardReadCommand.builder()
			.isEnabledOnly(isAdmin)
			.build();
		List<Board> boards = boardUseCase.readBoardList(boardReadCommand);
		return ResponseEntity.ok(BoardResponse.of("success", boards));
	}
}
