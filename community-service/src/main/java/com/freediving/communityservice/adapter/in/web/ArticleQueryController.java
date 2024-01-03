package com.freediving.communityservice.adapter.in.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class ArticleQueryController {
	// private final ArticleUseCase articleUseCase;
	//
	// @GetMapping("/boards/{boardId}/articles/{articleId}")
	// public ResponseEntity<ArticleContent> getArticleContent(
	// 	@PathVariable("boardId") String boardId,
	// 	@PathVariable("articleId") String articleId) {
	// 	// ArticleContent articleContent = articleUseCase.getArticle(boardId, articleId);
	// 	// return ResponseEntity.ok(articleContent);
	// 	return null;
	// }

}
