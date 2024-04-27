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
import com.freediving.communityservice.adapter.in.dto.CommentDeleteRequest;
import com.freediving.communityservice.adapter.in.dto.CommentEditRequest;
import com.freediving.communityservice.adapter.in.dto.CommentWriteRequest;
import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
import com.freediving.communityservice.application.port.in.CommentDeleteCommand;
import com.freediving.communityservice.application.port.in.CommentEditCommand;
import com.freediving.communityservice.application.port.in.CommentUseCase;
import com.freediving.communityservice.application.port.in.CommentWriteCommand;
import com.freediving.communityservice.domain.Comment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Comment 댓글", description = "댓글/답글 API")
@RequiredArgsConstructor
@RequestMapping("/v1")
@RestController
public class CommentCommandController {

	private final CommentUseCase commentUseCase;

	@Operation(
		summary = "댓글/답글 등록",
		description = "댓글 및 답글을 등록",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "댓글/답글 등록됨",
				useReturnTypeSchema = true
			)
		}
	)
	@PostMapping("/boards/{boardType}/articles/{articleId}/comments")
	public ResponseEntity<ResponseJsonObject<Comment>> writeComment(
		@Parameter(hidden = true) UserProvider userProvider,
		@PathVariable("boardType") BoardType boardType,
		@PathVariable("articleId") Long articleId,
		@RequestBody CommentWriteRequest commentWriteRequest
	) {
		Comment comment = commentUseCase.writeComment(
			CommentWriteCommand.builder()
				.requestUser(userProvider)
				.boardType(boardType)
				.articleId(articleId)
				.parentId(commentWriteRequest.getParentId())
				.content(commentWriteRequest.getContent())
				.visible(commentWriteRequest.isVisible())
				.build()
		);

		return ResponseEntity.ok(new ResponseJsonObject<>(ServiceStatusCode.OK, comment));
	}

	@Operation(
		summary = "댓글/답글 수정",
		description = "댓글/답글을 수정함",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "댓글/답글 수정됨",
				useReturnTypeSchema = true
			)
		}
	)
	@PostMapping("/boards/{boardType}/articles/{articleId}/comments/{commentId}")
	public ResponseEntity<ResponseJsonObject<Comment>> editComment(
		@Parameter(hidden = true) UserProvider userProvider,
		@PathVariable("boardType") BoardType boardType,
		@PathVariable("articleId") Long articleId,
		@PathVariable("commentId") Long commentId,
		@RequestBody CommentEditRequest commentEditRequest
	) {
		Comment comment = commentUseCase.editComment(
			CommentEditCommand.builder()
				.requestUser(userProvider)
				.boardType(boardType)
				.articleId(articleId)
				.parentId(commentEditRequest.getParentId())
				.commentId(commentId)
				.content(commentEditRequest.getContent())
				.visible(commentEditRequest.isVisible())
				.build()
		);

		return ResponseEntity.ok(new ResponseJsonObject<>(ServiceStatusCode.OK, comment));
	}

	@Operation(
		summary = "댓글/답글 삭제",
		description = "댓글/답글을 삭제함",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "댓글/답글 삭제됨",
				useReturnTypeSchema = true
			)
		}
	)
	@DeleteMapping("/boards/{boardType}/articles/{articleId}/comments/{commentId}")
	public ResponseEntity<ResponseJsonObject<Long>> removeComment(
		@Parameter(hidden = true) UserProvider userProvider,
		@PathVariable("boardType") BoardType boardType,
		@PathVariable("articleId") Long articleId,
		@PathVariable("commentId") Long commentId,
		@RequestBody CommentDeleteRequest commentDeleteRequest
	) {
		Long deletedId = commentUseCase.removeComment(
			CommentDeleteCommand.builder()
				.requestUser(userProvider)
				.boardType(boardType)
				.articleId(articleId)
				.parentId(commentDeleteRequest.getParentId())
				.commentId(commentId)
				.build()
		);

		return ResponseEntity.ok(new ResponseJsonObject<>(ServiceStatusCode.OK, deletedId));
	}
}
