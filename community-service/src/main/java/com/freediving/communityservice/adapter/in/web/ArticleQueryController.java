package com.freediving.communityservice.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.communityservice.adapter.out.external.article.ArticleContent;
import com.freediving.communityservice.application.port.in.ArticleUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class ArticleQueryController {
	private final ArticleUseCase articleUseCase;

	@GetMapping("/boards/{boardId}/articles/{articleId}")
	public ResponseEntity<ArticleContent> getArticleContent(
		@PathVariable("boardId") String boardId,
		@PathVariable("articleId") String articleId) {
		ArticleContent articleContent = articleUseCase.getArticle(boardId, articleId);
		return ResponseEntity.ok(articleContent);
	}

}
