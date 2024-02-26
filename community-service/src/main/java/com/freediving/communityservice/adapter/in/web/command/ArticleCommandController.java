package com.freediving.communityservice.adapter.in.web.command;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.freediving.communityservice.adapter.in.dto.ArticleEditRequest;
import com.freediving.communityservice.adapter.in.dto.ArticleWriteRequest;
import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.application.port.in.ArticleEditCommand;
import com.freediving.communityservice.application.port.in.ArticleRemoveCommand;
import com.freediving.communityservice.application.port.in.ArticleUseCase;
import com.freediving.communityservice.application.port.in.ArticleWriteCommand;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1.0")
@RestController
public class ArticleCommandController {

	private final ArticleUseCase articleUseCase;

	@PostMapping("/boards/{boardId}/articles")
	public ResponseEntity<Long> writeArticleContent(
		UserProvider userProvider,
		@PathVariable("boardId") Long boardId,
		@RequestBody ArticleWriteRequest articleWriteRequest) {
		Long articleId = articleUseCase.writeArticle(
			ArticleWriteCommand.builder()
				.userProvider(userProvider)
				.boardId(boardId)
				.title(articleWriteRequest.getTitle())
				.content(articleWriteRequest.getContent())
				.authorName(articleWriteRequest.getAuthorName())
				// .hashtagIds(articleWriteRequest.getHashtagIds())
				.enableComment(articleWriteRequest.isEnableComment())
				.build());

		// return ResponseEntity.ok(articleId);
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(articleId)
			.toUri();
		return ResponseEntity.created(location).build();
	}

	@PostMapping("/boards/{boardId}/articles/{articleId}")
	public ResponseEntity<Long> editArticleContent(
		UserProvider userProvider,
		@PathVariable("boardId") Long boardId,
		@PathVariable("articleId") Long articleId,
		@RequestBody ArticleEditRequest articleEditRequest
	) {
		Long editedArticleId = articleUseCase.editArticle(
			ArticleEditCommand.builder()
				.userProvider(userProvider)
				.boardId(boardId)
				.articleId(articleId)
				.title(articleEditRequest.getTitle())
				.content(articleEditRequest.getContent())
				.hashtagIds(articleEditRequest.getHashtagIds())
				.enableComment(articleEditRequest.isEnableComment())
				.build()
		);

		return ResponseEntity.ok( editedArticleId);
	}

	@DeleteMapping("/boards/{boardId}/articles/{articleId}")
	public ResponseEntity<Long> removeArticle(
		UserProvider userProvider,
		@PathVariable("boardId") Long boardId,
		@PathVariable("articleId") Long articleId
	) {

		Long deletedArticleId = articleUseCase.deleteArticle(
			ArticleRemoveCommand.builder()
				.userProvider(userProvider)
				.boardId(boardId)
				.articleId(articleId)
				.build()
		);
		return ResponseEntity.ok(1L);
	}
}
