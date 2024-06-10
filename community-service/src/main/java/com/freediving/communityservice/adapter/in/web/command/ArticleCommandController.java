package com.freediving.communityservice.adapter.in.web.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.communityservice.adapter.in.dto.ArticleEditRequest;
import com.freediving.communityservice.adapter.in.dto.ArticleWriteRequest;
import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
import com.freediving.communityservice.application.port.in.ArticleEditCommand;
import com.freediving.communityservice.application.port.in.ArticleRemoveCommand;
import com.freediving.communityservice.application.port.in.ArticleUseCase;
import com.freediving.communityservice.application.port.in.ArticleWriteCommand;
import com.freediving.communityservice.application.port.in.dto.ImageInfoCommand;
import com.freediving.communityservice.config.type.CacheType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Article 게시글", description = "게시글 API")
@RequiredArgsConstructor
@RequestMapping("/v1")
@RestController
public class ArticleCommandController {

	private final CacheManager cacheManager;
	private final ArticleUseCase articleUseCase;

	@Value("${community.gateway.fqdn}")
	private String GATEWAY_DOMAIN;

	// TODO: 게시글 생성 시 GET 요청하여 UUID 발급 후, 서버 인메모리 저장. => 게시글 중복생성 방지 , 등록 취소시 업로드 이미지 삭제.
	@Operation(
		summary = "게시글 등록",
		description = "게시글 (이미지 포함)을 등록",
		responses = {
			@ApiResponse(
				responseCode = "201",
				description = "게시글 등록됨",
				useReturnTypeSchema = true
			)
		}
	)
	@PostMapping("/boards/{boardType}/articles")
	public ResponseEntity<ResponseJsonObject<Long>> writeArticleContent(
		@Parameter(hidden = true) UserProvider userProvider,
		@PathVariable("boardType") BoardType boardType,
		@RequestBody ArticleWriteRequest articleWriteRequest) {

		String cacheKey = CacheType.ARTICLE_CREATE_TIME_LIMIT.getCacheName() + ":" + userProvider.getRequestUserId();
		Cache cache = cacheManager.getCache(CacheType.ARTICLE_CREATE_TIME_LIMIT.getCacheName());

		Optional<Object> cachedValue = Optional.ofNullable(cache.get(cacheKey, Object.class));
		if (cachedValue.isPresent()) {
			throw new BuddyMeException(ServiceStatusCode.BAD_REQUEST,
				String.format("%d초 이후 글 생성이 가능합니다.", CacheType.ARTICLE_CREATE_TIME_LIMIT.getExpiredSecondAfterWrite())
			);
		}

		List<ImageInfoCommand> uploadedImages =
			CollectionUtils.isEmpty(articleWriteRequest.getImages()) ? new ArrayList<ImageInfoCommand>() :
				articleWriteRequest.getImages()
					.stream()
					.map(image -> new ImageInfoCommand(image.getSortNumber(), image.getUrl()))
					.toList();

		Long articleId = articleUseCase.writeArticle(
			ArticleWriteCommand.builder()
				.userProvider(userProvider)
				.boardType(boardType)
				.title(articleWriteRequest.getTitle())
				.content(articleWriteRequest.getContent())
				.enableComment(articleWriteRequest.isEnableComment())
				.images(uploadedImages)
				.build());

		cache.put(cacheKey, '1');

		return ResponseEntity.ok(new ResponseJsonObject<>(ServiceStatusCode.OK, articleId));
	}

	@Operation(
		summary = "게시글 내용 수정",
		description = "게시글 (이미지 포함)을 수정",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "게시글 수정됨",
				useReturnTypeSchema = true
			)
		}
	)
	@PostMapping("/boards/{boardType}/articles/{articleId}")
	public ResponseEntity<ResponseJsonObject<Long>> editArticleContent(
		@Parameter(hidden = true) UserProvider userProvider,
		@PathVariable("boardType") BoardType boardType,
		@PathVariable("articleId") Long articleId,
		@RequestBody ArticleEditRequest articleEditRequest
	) {
		List<ImageInfoCommand> contentImages =
			CollectionUtils.isEmpty(articleEditRequest.getImages()) ? new ArrayList<ImageInfoCommand>() :
				articleEditRequest.getImages()
					.stream()
					.map(image -> new ImageInfoCommand(image.getSortNumber(), image.getUrl()))
					.toList();

		Long editedArticleId = articleUseCase.editArticle(
			ArticleEditCommand.builder()
				.userProvider(userProvider)
				.boardType(boardType)
				.articleId(articleId)
				.title(articleEditRequest.getTitle())
				.content(articleEditRequest.getContent())
				.enableComment(articleEditRequest.isEnableComment())
				.images(contentImages)
				.build()
		);

		return ResponseEntity.ok(new ResponseJsonObject<>(ServiceStatusCode.OK, editedArticleId));
	}

	@Operation(
		summary = "게시글 삭제",
		description = "게시글 (이미지, 댓글 포함)을 삭제",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "게시글 삭제됨",
				useReturnTypeSchema = true
			)
		}
	)
	@DeleteMapping("/boards/{boardType}/articles/{articleId}")
	public ResponseEntity<ResponseJsonObject<Long>> removeArticle(
		@Parameter(hidden = true) UserProvider userProvider,
		@PathVariable("boardType") BoardType boardType,
		@PathVariable("articleId") Long articleId
	) {

		Long deletedArticleId = articleUseCase.deleteArticle(
			ArticleRemoveCommand.builder()
				.userProvider(userProvider)
				.boardType(boardType)
				.articleId(articleId)
				.build()
		);
		return ResponseEntity.ok(new ResponseJsonObject<>(ServiceStatusCode.OK, deletedArticleId));
	}

}
