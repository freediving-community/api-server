package com.freediving.communityservice.adapter.in.web.command;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.communityservice.adapter.in.dto.BoardWriteRequest;
import com.freediving.communityservice.adapter.out.dto.board.BoardResponse;
import com.freediving.communityservice.application.port.in.BoardUseCase;
import com.freediving.communityservice.application.port.in.BoardWriteCommand;
import com.freediving.communityservice.domain.Board;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1.0")
@RestController
public class BoardCommandController {
	// 관리자만 접근 가능
	//TODO SelfValidating https://velog.io/@byeongju/Bean-Validation-%EB%B6%88%ED%8E%B8%ED%95%A8-%ED%95%B4%EA%B2%B0%ED%95%98%EA%B8%B0
	private final BoardUseCase boardUseCase;

	@PostMapping("/boards")
	public ResponseEntity<BoardResponse> writeBoard(@RequestBody BoardWriteRequest boardWriteRequest) {

		System.out.println(boardWriteRequest.toString());
		BoardWriteCommand boardWriteCommand = BoardWriteCommand.builder()
			.boardName(boardWriteRequest.getBoardName())
			.boardType(boardWriteRequest.getBoardType())
			.description(boardWriteRequest.getDescription())
			.sortOrder(boardWriteRequest.getSortOrder())
			.build();

		Board board = boardUseCase.createBoard(boardWriteCommand);
		BoardResponse res = BoardResponse.of("success", board);
		return ResponseEntity.ok(res);
		//return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
