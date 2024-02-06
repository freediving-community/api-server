package com.freediving.communityservice.adapter.in.web.command;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.communityservice.adapter.in.dto.CommentWriteRequest;
import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.dto.comment.CommentResponse;
import com.freediving.communityservice.application.port.in.CommentUseCase;
import com.freediving.communityservice.application.port.in.CommentWriteCommand;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1.0")
@RestController
public class CommentCommandController {

	private final CommentUseCase commentUseCase;

	@PostMapping("/boards/{boardId}/articles/{articleId}/comments")
	public ResponseEntity<CommentResponse> writeComment(
		UserProvider userProvider,
		@PathVariable("boardId") Long boardId,
		@PathVariable("articleId") Long articleId,
		@RequestBody CommentWriteRequest commentWriteRequest
	) {
		CommentResponse comment = commentUseCase.writeComment(
			CommentWriteCommand.builder()
				.requestUser(userProvider)
				.boardId(boardId)
				.articleId(articleId)
				.parentId(commentWriteRequest.getParentId())
				.content(commentWriteRequest.getContent())
				.visible(commentWriteRequest.isVisible())
				.build()
		);
		return ResponseEntity.ok(comment);
	}

}
