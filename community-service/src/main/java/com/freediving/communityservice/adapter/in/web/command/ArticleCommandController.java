package com.freediving.communityservice.adapter.in.web.command;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.freediving.communityservice.adapter.in.dto.ArticleWriteRequest;
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
		@PathVariable("boardId") Long boardId,
		@RequestBody ArticleWriteRequest articleWriteRequest) {
		Long articleId = articleUseCase.writeArticle(
			ArticleWriteCommand.builder()
				.boardId(boardId)
				.title(articleWriteRequest.getTitle())
				.content(articleWriteRequest.getContent())
				.authorName(articleWriteRequest.getAuthorName())
				.enableComment(articleWriteRequest.isEnableComment())
				.createdBy(articleWriteRequest.getCreatedBy())
				.build());

		// return ResponseEntity.ok(articleId);
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(articleId)
			.toUri();
		return ResponseEntity.created(location).build();
	}
}
