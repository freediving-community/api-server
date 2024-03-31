package com.freediving.communityservice.adapter.in.web.query;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.dto.article.ArticleBriefDto;
import com.freediving.communityservice.adapter.out.dto.article.ArticleContent;
import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
import com.freediving.communityservice.application.port.in.ArticleIndexListCommand;
import com.freediving.communityservice.application.port.in.ArticleReadCommand;
import com.freediving.communityservice.application.port.in.ArticleUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class ArticleQueryController {

	private final ArticleUseCase articleUseCase;

	@GetMapping("/boards/{boardType}/articles")
	public ResponseEntity<ResponseJsonObject<Page<ArticleBriefDto>>> getArticleList(
		UserProvider userProvider,
		@PathVariable("boardType") BoardType boardType,
		@RequestParam(value = "page", required = false, defaultValue = "1") int page,
		@RequestParam(value = "offset", required = false, defaultValue = "10") int offset,
		@RequestParam(value = "orderBy", required = false, defaultValue = "createdAt") String orderBy,
		@RequestParam(value = "c", required = false, defaultValue = "") Long cursor
	) {
		Page<ArticleBriefDto> articleIndexList = articleUseCase.getArticleIndexList(
			ArticleIndexListCommand.builder()
				.userProvider(userProvider)
				.boardType(boardType)
				.page(page)
				.offset(offset)
				.orderBy(orderBy)
				.cursor(cursor)
				.build()
		);

		// new ArticleContentWithComment(Article.builder().id(123L).content("내용").build(), null);

		ResponseJsonObject<Page<ArticleBriefDto>> response = new ResponseJsonObject<>(ServiceStatusCode.OK,
			articleIndexList);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/boards/{boardType}/articles/{articleId}")
	public ResponseEntity<ResponseJsonObject<ArticleContent>> getArticleContent(
		UserProvider userProvider,
		@PathVariable("boardType") BoardType boardType,
		@PathVariable("articleId") Long articleId,
		@RequestParam(value = "showAll", required = false, defaultValue = "false") boolean showAll,
		@RequestParam(value = "articleOnly", required = false, defaultValue = "false") boolean withoutComment) {
		//TODO Cursor Based Query 적용 https://velog.io/@znftm97/%EC%BB%A4%EC%84%9C-%EA%B8%B0%EB%B0%98-%ED%8E%98%EC%9D%B4%EC%A7%80%EB%84%A4%EC%9D%B4%EC%85%98Cursor-based-Pagination%EC%9D%B4%EB%9E%80-Querydsl%EB%A1%9C-%EA%B5%AC%ED%98%84%EA%B9%8C%EC%A7%80-so3v8mi2

		// if (showAll) {
		// 	userProvider.checkAdmin();
		// }

		ArticleContent articleContentDetail = articleUseCase.getArticleWithComment(
			ArticleReadCommand.builder()
				.userProvider(userProvider)
				.boardType(boardType)
				.articleId(articleId)
				.isShowAll(showAll)
				.withoutComment(withoutComment)
				.build());
		// return ResponseEntity.ok(articleContent);
		return ResponseEntity.ok(new ResponseJsonObject<>(ServiceStatusCode.OK, articleContentDetail));
	}

}
