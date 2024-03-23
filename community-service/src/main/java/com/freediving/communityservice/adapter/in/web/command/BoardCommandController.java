package com.freediving.communityservice.adapter.in.web.command;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.communityservice.adapter.in.dto.BoardWriteRequest;
import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.dto.board.BoardResponse;
import com.freediving.communityservice.application.port.in.BoardUseCase;
import com.freediving.communityservice.application.port.in.BoardWriteCommand;
import com.freediving.communityservice.domain.Board;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class BoardCommandController {

	private final BoardUseCase boardUseCase;

	@PostMapping("/boards")
	public ResponseEntity<BoardResponse> writeBoard(
		UserProvider userProvider,
		@RequestBody BoardWriteRequest boardWriteRequest
	) {

		//TODO AdminOnly Role 공통화 처리 예정
		if (!userProvider.getRequestUserId().equals(22091008L)) {
			throw new IllegalArgumentException("Admin만 가능함.");
		}

		BoardWriteCommand boardWriteCommand = BoardWriteCommand.builder()
			.boardName(boardWriteRequest.getBoardName())
			.boardType(boardWriteRequest.getBoardType())
			.description(boardWriteRequest.getDescription())
			.sortOrder(boardWriteRequest.getSortOrder())
			.build();

		Board board = boardUseCase.createBoard(boardWriteCommand);
		BoardResponse res = BoardResponse.of("success", board);
		return ResponseEntity.ok(res);
	}

}
