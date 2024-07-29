package com.freediving.communityservice.adapter.in.web.chat;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.common.response.ResponseJsonObject;
import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.dto.chat.ChatResponse;
import com.freediving.communityservice.adapter.out.persistence.constant.ChatType;
import com.freediving.communityservice.application.port.in.ChatRoomCommand;
import com.freediving.communityservice.application.port.in.ChatUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "ChatRoom 채팅방", description = "채팅 일괄 API")
@RequiredArgsConstructor
@RequestMapping("/v1")
@RestController
public class ChatRoomController {

	private final ChatUseCase chatUseCase;

	@Operation(
		summary = "버디 채팅방 요청 (조회 및 생성)",
		description = "채팅방 입장, 생성이 유효한 조건에서 없는 경우 생성하면서 응답",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "채팅방 입장",
				useReturnTypeSchema = true
			)
		}
	)
	@GetMapping("/chat/buddy/{buddyEventId}")
	public ResponseEntity<ResponseJsonObject<Long>> requestBuddyChatRoom(
		@Parameter(hidden = true) UserProvider userProvider,
		@PathVariable("buddyEventId") Long buddyEventId
	) {
		ChatResponse chatRoom = chatUseCase.requestChatRoom(
			ChatRoomCommand.builder()
				.userProvider(userProvider)
				.chatType(ChatType.BUDDY)
				.buddyEventId(buddyEventId)
				.build()
		);
		return null;
		// return ResponseEntity.ok(new ResponseJsonObject<>(ServiceStatusCode.OK, articleId));
	}

/*
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
	}*/

}
