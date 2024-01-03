package com.freediving.communityservice.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.communityservice.adapter.in.dto.MakeBoardRequest;
import com.freediving.communityservice.adapter.out.dto.board.BoardResponse;
import com.freediving.communityservice.application.port.in.MakeBoardCommand;
import com.freediving.communityservice.application.port.in.query.MakeBoardUseCase;
import com.freediving.communityservice.domain.Board;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1.0")
@RestController
public class BoardCommandController {
	//TODO SelfValidating https://velog.io/@byeongju/Bean-Validation-%EB%B6%88%ED%8E%B8%ED%95%A8-%ED%95%B4%EA%B2%B0%ED%95%98%EA%B8%B0
	private final MakeBoardUseCase makeBoardUseCase;

	@PostMapping("/boards")
	public ResponseEntity<BoardResponse> makeBoard(@RequestBody MakeBoardRequest makeBoardRequest) {

		System.out.println(makeBoardRequest.toString());
		MakeBoardCommand makeBoardCommand = MakeBoardCommand.builder()
			.boardName(makeBoardRequest.getBoardName())
			.boardType(makeBoardRequest.getBoardType())
			.description(makeBoardRequest.getDescription())
			.sortOrder(makeBoardRequest.getSortOrder())
			.build();

		Board board = makeBoardUseCase.createBoard(makeBoardCommand);
		BoardResponse res = BoardResponse.of("success", board);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
