package com.freediving.communityservice.adapter.in.web.query;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
import com.freediving.communityservice.application.port.in.CommentReadCommand;
import com.freediving.communityservice.application.port.in.CommentUseCase;
import com.freediving.communityservice.domain.Comment;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class CommentQueryController {

	private final CommentUseCase commentUseCase;

	@GetMapping("/boards/{boardType}/articles/{articleId}/comments")
	public ResponseEntity<Comment> getCommentsOfArticle(
		UserProvider userProvider,
		@PathVariable("boardType") BoardType boardType,
		@PathVariable("articleId") Long articleId
	) {
		Comment comment = commentUseCase.readComments(CommentReadCommand.builder()
			.requestUser(userProvider)
			.boardType(boardType)
			.articleId(articleId)
			.build());
		return ResponseEntity.ok(comment);
	}

}
