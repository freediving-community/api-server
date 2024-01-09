package com.freediving.communityservice.adapter.in.web.query;

import java.util.List;

import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/v1.0")
@RestController
public class BoardQueryController {
	//TODO SelfValidating https://velog.io/@byeongju/Bean-Validation-%EB%B6%88%ED%8E%B8%ED%95%A8-%ED%95%B4%EA%B2%B0%ED%95%98%EA%B8%B0
	public static class SampleResponse {
		String sample;
	}

	private final BoardUseCase boardUseCase;

	@GetMapping("/boards")
	public ResponseEntity<List<BoardResponse>> getBoards() {
		boolean isAdmin = false;
		// boolean isAdmin = checkAdmin(principal); // 관리자인지 확인하는 로직
		// BoardReadCommand command = new BoardReadCommand(isAdmin && (showAll == null || showAll));

		BoardReadCommand boardReadCommand = BoardReadCommand.builder()
			.isEnabledOnly(isAdmin)
			.build();

		List<Board> boards = boardUseCase.readBoardList(boardReadCommand);

		List<BoardResponse> boards = boardService.getBoards(command);
		SampleResponse res = new SampleResponse();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/boards/{boardId}")
	public ResponseEntity<SampleResponse> getBoardDetail(@PathVariable Long boardId) {
		SampleResponse res = new SampleResponse();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
