package com.freediving.communityservice.adapter.in.web.command;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.communityservice.adapter.in.dto.CommentWriteRequest;
import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
import com.freediving.communityservice.application.port.in.CommentUseCase;
import com.freediving.communityservice.application.port.in.CommentWriteCommand;
import com.freediving.communityservice.domain.Comment;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class CommentCommandController {

	private final CommentUseCase commentUseCase;

	@PostMapping("/boards/{boardType}/articles/{articleId}/comments")
	public ResponseEntity<Comment> writeComment(
		UserProvider userProvider,
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

		return ResponseEntity.ok(comment);
	}

}
