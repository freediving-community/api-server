package com.freediving.communityservice.adapter.in.web.query;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.communityservice.adapter.out.dto.board.BoardResponse;
import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
import com.freediving.communityservice.application.port.in.BoardReadCommand;
import com.freediving.communityservice.application.port.in.BoardUseCase;
import com.freediving.communityservice.domain.Board;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Board 게시판", description = "게시판 조회 API")
@RequiredArgsConstructor
@RequestMapping("/v1")
@RestController
public class BoardQueryController {

	private final BoardUseCase boardUseCase;

	@Operation(
		summary = "게시판 목록 조회",
		description = "게시판 목록 조회",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "게시판 목록 조회됨",
				useReturnTypeSchema = true
			)
		}
	)
	@GetMapping("/boards")
	public ResponseEntity<ResponseJsonObject<BoardResponse>> getBoards() {
		boolean isAdmin = false;
		// boolean isAdmin = checkAdmin(principal); //TODO 관리자인지 확인하는 로직
		BoardReadCommand boardReadCommand = BoardReadCommand.builder()
			.isEnabledOnly(isAdmin)
			.build();
		List<Board> boards = boardUseCase.readBoardList(boardReadCommand);
		return ResponseEntity.ok(new ResponseJsonObject<>(ServiceStatusCode.OK, BoardResponse.of("success", boards)));
	}

	@Operation(
		summary = "게시판 상세 조회",
		description = "게시판 상세 정보 조회",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "게시판 상세 정보 조회됨",
				useReturnTypeSchema = true
			)
		}
	)
	@GetMapping("/boards/{boardType}")
	public ResponseEntity<ResponseJsonObject<BoardResponse>> getBoardDetail(
		@PathVariable("boardType") BoardType boardType) {
		Optional<Board> board = boardUseCase.readBoard(boardType);
		return ResponseEntity.ok(new ResponseJsonObject<>(ServiceStatusCode.OK,
			BoardResponse.of("success", board.orElseThrow())));
	}
}
