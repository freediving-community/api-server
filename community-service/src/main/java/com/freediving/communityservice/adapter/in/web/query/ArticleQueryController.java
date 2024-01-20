package com.freediving.communityservice.adapter.in.web.query;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.communityservice.adapter.out.dto.article.ArticleContent;
import com.freediving.communityservice.application.port.in.ArticleReadCommand;
import com.freediving.communityservice.application.port.in.ArticleUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class ArticleQueryController {

	private final ArticleUseCase articleUseCase;

	@GetMapping("/boards/{boardId}/articles/{articleId}")
	public ResponseEntity<ArticleContent> getArticleContent(
		@PathVariable("boardId") Long boardId,
		@PathVariable("articleId") Long articleId,
		@RequestParam(value = "articleOnly", required = false) boolean noComment) {

		//TODO Cursor Based Query 적용 https://velog.io/@znftm97/%EC%BB%A4%EC%84%9C-%EA%B8%B0%EB%B0%98-%ED%8E%98%EC%9D%B4%EC%A7%80%EB%84%A4%EC%9D%B4%EC%85%98Cursor-based-Pagination%EC%9D%B4%EB%9E%80-Querydsl%EB%A1%9C-%EA%B5%AC%ED%98%84%EA%B9%8C%EC%A7%80-so3v8mi2

		ArticleContent articleContent = articleUseCase.getArticle(ArticleReadCommand.builder().build());
		// return ResponseEntity.ok(articleContent);
		return null;
	}

}
