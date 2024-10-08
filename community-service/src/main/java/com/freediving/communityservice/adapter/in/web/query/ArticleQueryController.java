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
import com.freediving.communityservice.adapter.out.dto.article.ArticleBriefResponse;
import com.freediving.communityservice.adapter.out.dto.article.ArticleContentWithCommentResponse;
import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
import com.freediving.communityservice.application.port.in.ArticleIndexListCommand;
import com.freediving.communityservice.application.port.in.ArticleReadCommand;
import com.freediving.communityservice.application.port.in.ArticleUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Article 게시글", description = "게시글 API")
@RequiredArgsConstructor
@RequestMapping("/v1")
@RestController
public class ArticleQueryController {

	private final ArticleUseCase articleUseCase;

	@Operation(
		summary = "게시글 목록 조회",
		description = "게시글 목록과 각 게시글의 첫 번째 사진을 조회",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "게시글 목록 조회됨",
				useReturnTypeSchema = true
			)
		}
	)
	@GetMapping("/boards/{boardType}/articles")
	public ResponseEntity<ResponseJsonObject<Page<ArticleBriefResponse>>> getArticleList(
		@Parameter(hidden = true) UserProvider userProvider,
		@Parameter(description = "게시판 유형", required = true) @PathVariable("boardType") BoardType boardType,
		// @RequestParam(value = "page", required = false, defaultValue = "1") int page,
		@Parameter(description = "한 번에 가져올 갯수") @RequestParam(value = "offset", required = false, defaultValue = "20") int offset,
		@Parameter(description = "createdAt(default)생성시각, liked 좋아요수, comment 댓글수, hits 조회수 (전체-DESC)")
		@RequestParam(value = "orderBy", required = false, defaultValue = "createdAt") String orderBy,
		@Parameter(description = "마지막 조회한 게시글 ID") @RequestParam(value = "c", required = false, defaultValue = "") Long cursor,
		@Parameter(description = "이미지가 있는 게시글만 가져올 지 여부") @RequestParam(value = "onlyPicture", required = false) boolean onlyPicture,
		@Parameter(description = "특정 사용자의 게시글만 조회") @RequestParam(value = "userId", required = false) Long userId
	) {
		Page<ArticleBriefResponse> articleIndexList = articleUseCase.getArticleIndexList(
			ArticleIndexListCommand.builder()
				.userProvider(userProvider)
				.boardType(boardType)
				.page(1)
				.offset(offset)
				.onlyPicture(onlyPicture)
				.orderBy(orderBy)
				.cursor(cursor)
				.userId(userId)
				.build()
		);
		return ResponseEntity.ok(new ResponseJsonObject<>(ServiceStatusCode.OK, articleIndexList));
	}

	@Operation(
		summary = "게시글 상세 조회",
		description = "게시글과 이미지, 댓글 [,로그인 사용자의 좋아요 여부]등을 상세 조회 * Header에 User-Id가 있는 경우 로그인 사용자의 댓글(비밀댓글)을 먼저 가져오며 좋아요 여부를 응답한다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "게시글 상세 조회됨",
				useReturnTypeSchema = true
			)
		}
	)
	@GetMapping("/boards/{boardType}/articles/{articleId}")
	public ResponseEntity<ResponseJsonObject<ArticleContentWithCommentResponse>> getArticleContent(
		@Parameter(hidden = true) UserProvider userProvider,
		@PathVariable("boardType") BoardType boardType,
		@PathVariable("articleId") Long articleId,
		@RequestParam(value = "showAll", required = false, defaultValue = "false") boolean showAll,
		@RequestParam(value = "articleOnly", required = false, defaultValue = "false") boolean withoutComment) {

		ArticleContentWithCommentResponse articleContentResponseDetail = articleUseCase.getArticleWithComment(
			ArticleReadCommand.builder()
				.userProvider(userProvider)
				.boardType(boardType)
				.articleId(articleId)
				.isShowAll(showAll)
				.withoutComment(withoutComment)
				.build());
		return ResponseEntity.ok(new ResponseJsonObject<>(ServiceStatusCode.OK, articleContentResponseDetail));
	}
}
