// package com.freediving.communityservice.adapter.in.web.command;
//
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// import com.freediving.communityservice.adapter.in.dto.BoardWriteRequest;
// import com.freediving.communityservice.adapter.in.web.UserProvider;
// import com.freediving.communityservice.adapter.out.dto.board.BoardResponse;
// import com.freediving.communityservice.application.port.in.BoardUseCase;
// import com.freediving.communityservice.application.port.in.BoardWriteCommand;
// import com.freediving.communityservice.domain.Board;
//
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import lombok.RequiredArgsConstructor;
//
// @RequiredArgsConstructor
// @RequestMapping("/v1")
// @RestController
// public class BoardCommandController {
//
// 	private final BoardUseCase boardUseCase;
//
// 	@Operation(
// 		summary = "게시판 등록",
// 		description = "게시판을 생성하지만, 게시판별로 정책과 서버 코드 수정이 필요하여 직접 관리",
// 		responses = {
// 			@ApiResponse(
// 				responseCode = "200",
// 				description = "게시글 등록됨",
// 				useReturnTypeSchema = true
// 			)
// 		}
// 	)//@Parameter(hidden = true)
// 	@PostMapping("/boards")
// 	public ResponseEntity<BoardResponse> writeBoard(
// 		UserProvider userProvider,
// 		@RequestBody BoardWriteRequest boardWriteRequest
// 	) {
//
// 		if (!userProvider.getRequestUserId().equals(1111L)) {
// 			throw new IllegalArgumentException("Admin만 가능함.");
// 		}
//
// 		BoardWriteCommand boardWriteCommand = BoardWriteCommand.builder()
// 			.boardName(boardWriteRequest.getBoardName())
// 			.boardType(boardWriteRequest.getBoardType())
// 			.description(boardWriteRequest.getDescription())
// 			.sortOrder(boardWriteRequest.getSortOrder())
// 			.build();
//
// 		Board board = boardUseCase.createBoard(boardWriteCommand);
// 		BoardResponse res = BoardResponse.of("success", board);
// 		return ResponseEntity.ok(res);
// 	}
//
// }
