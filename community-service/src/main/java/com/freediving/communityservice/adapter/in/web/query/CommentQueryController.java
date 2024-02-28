package com.freediving.communityservice.adapter.in.web.query;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.application.port.in.CommentReadCommand;
import com.freediving.communityservice.application.port.in.CommentUseCase;
import com.freediving.communityservice.domain.Comment;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1.0")
@RestController
public class CommentQueryController {

	private final CommentUseCase commentUseCase;

	@GetMapping("/boards/{boardId}/articles/{articleId}/comments")
	public ResponseEntity<Comment> getCommentsOfArticle(
		UserProvider userProvider,
		@PathVariable("boardId") Long boardId,
		@PathVariable("articleId") Long articleId
	) {
		Comment comment = commentUseCase.readComments(CommentReadCommand.builder()
			.requestUser(userProvider)
			.boardId(boardId)
			.articleId(articleId)
			.build());
		return ResponseEntity.ok(comment);
	}

}