package com.freediving.communityservice.adapter.in.web.command;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.communityservice.adapter.in.dto.UserReactionRequest;
import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
import com.freediving.communityservice.adapter.out.persistence.constant.UserReactionType;
import com.freediving.communityservice.application.port.in.UserReactionCommand;
import com.freediving.communityservice.application.port.in.UserReactionUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "UserReaction 좋아요", description = "유저 상호작용(좋아요) API")
@RequiredArgsConstructor
@RequestMapping("/v1")
@RestController
public class UserReactionCommandController {

	private final UserReactionUseCase userReactionUseCase;

	@Operation(
		summary = "좋아요 등록",
		description = "좋아요를 등록",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "좋아요 등록됨",
				useReturnTypeSchema = true
			)
		}
	)
	@PostMapping("/boards/{boardType}/articles/{articleId}/reaction")
	public ResponseEntity<ResponseJsonObject<Object>> recordUserReaction(
		@Parameter(hidden = true) UserProvider userProvider,
		@PathVariable("boardType") BoardType boardType,
		@PathVariable("articleId") Long articleId,
		@RequestBody UserReactionRequest userReactionRequest
	) {
		UserReactionType userReactionType = userReactionUseCase.recordUserReaction(
			UserReactionCommand.builder()
				.userProvider(userProvider)
				.boardType(boardType)
				.articleId(articleId)
				.userReactionType(userReactionRequest.getUserReactionType())
				.build()
		);

		// TODO API 직접 요청, 중반복 등에 대한 처리 후 메세지 정리

		ResponseJsonObject<Object> response = ResponseJsonObject.builder()
			.data(userReactionType)
			.code(ServiceStatusCode.OK)
			.expandMsg(userReactionType.name())
			.build();
		return ResponseEntity.ok(response);
	}

	@Operation(
		summary = "좋아요 취소",
		description = "좋아요를 취소(삭제)",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "좋아요 취소됨",
				useReturnTypeSchema = true
			)
		}
	)
	@DeleteMapping("/boards/{boardType}/articles/{articleId}/reaction")
	public ResponseEntity<ResponseJsonObject<Object>> deleteUserReaction(
		@Parameter(hidden = true) UserProvider userProvider,
		@PathVariable("boardType") BoardType boardType,
		@PathVariable("articleId") Long articleId,
		@RequestBody UserReactionRequest userReactionRequest
	) {
		int successCode = userReactionUseCase.deleteUserReaction(
			UserReactionCommand.builder()
				.userProvider(userProvider)
				.boardType(boardType)
				.articleId(articleId)
				.userReactionType(userReactionRequest.getUserReactionType())
				.build()
		);
		String responseMessage = "처리 실패";
		if (successCode > 0) {
			responseMessage = userReactionRequest.getUserReactionType().name();
		}
		ResponseJsonObject<Object> response = ResponseJsonObject.builder()
			.data(responseMessage)
			.code(ServiceStatusCode.OK)
			.build();
		return ResponseEntity.ok(response);
	}

}
