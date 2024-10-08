package com.freediving.communityservice.application.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.communityservice.adapter.out.dto.article.ArticleBriefResponse;
import com.freediving.communityservice.adapter.out.dto.article.ArticleContentWithCommentResponse;
import com.freediving.communityservice.adapter.out.dto.comment.CommentResponse;
import com.freediving.communityservice.adapter.out.dto.image.ImageResponse;
import com.freediving.communityservice.adapter.out.dto.user.UserInfo;
import com.freediving.communityservice.adapter.out.persistence.constant.UserReactionType;
import com.freediving.communityservice.adapter.out.persistence.userreact.UserReactionId;
import com.freediving.communityservice.application.port.in.ArticleEditCommand;
import com.freediving.communityservice.application.port.in.ArticleIndexListCommand;
import com.freediving.communityservice.application.port.in.ArticleReadCommand;
import com.freediving.communityservice.application.port.in.ArticleRemoveCommand;
import com.freediving.communityservice.application.port.in.ArticleUseCase;
import com.freediving.communityservice.application.port.in.ArticleWriteCommand;
import com.freediving.communityservice.application.port.in.dto.ImageInfoCommand;
import com.freediving.communityservice.application.port.out.ArticleDeletePort;
import com.freediving.communityservice.application.port.out.ArticleEditPort;
import com.freediving.communityservice.application.port.out.ArticleReadPort;
import com.freediving.communityservice.application.port.out.ArticleWritePort;
import com.freediving.communityservice.application.port.out.BoardReadPort;
import com.freediving.communityservice.application.port.out.CommentDeletePort;
import com.freediving.communityservice.application.port.out.CommentReadPort;
import com.freediving.communityservice.application.port.out.ImageDeletePort;
import com.freediving.communityservice.application.port.out.ImageEditPort;
import com.freediving.communityservice.application.port.out.ImageReadPort;
import com.freediving.communityservice.application.port.out.ImageWritePort;
import com.freediving.communityservice.application.port.out.UserReactionPort;
import com.freediving.communityservice.application.port.out.external.MemberFeignPort;
import com.freediving.communityservice.domain.Article;
import com.freediving.communityservice.domain.Board;
import com.freediving.communityservice.domain.Comment;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService implements ArticleUseCase {

	private final BoardReadPort boardReadPort;
	private final ArticleWritePort articleWritePort;
	private final ArticleReadPort articleReadPort;
	private final ArticleEditPort articleEditPort;
	private final ArticleDeletePort articleDeletePort;
	private final CommentReadPort commentReadPort;
	private final CommentDeletePort commentDeletePort;
	private final UserReactionPort userReactionPort;
	private final ImageWritePort imageWritePort;
	private final ImageReadPort imageReadPort;
	private final ImageEditPort imageEditPort;
	private final ImageDeletePort imageDeletePort;
	private final MemberFeignPort memberFeignPort;

	@Override
	public ArticleContentWithCommentResponse getArticleWithComment(ArticleReadCommand command) {

		Article article = articleReadPort.readArticle(
			command.getBoardType(),
			command.getArticleId(),
			command.isShowAll()
		);
		article.increaseViewCount();
		articleReadPort.increaseViewCount(article.getBoardType(), article.getId());

		List<ImageResponse> images = imageReadPort.getImageListByArticle(article.getId());

		Map<Long, UserInfo> articleOwner = memberFeignPort.getUserMapByUserIds(List.of(article.getCreatedBy()), true);

		if (command.isWithoutComment()) { // 본문 내용 수정 등
			return ArticleContentWithCommentResponse.of(
				article,
				articleOwner.get(article.getCreatedBy()),
				images,
				0,
				null,
				false
			);
		}

		int allCommentCount = commentReadPort.getCommentCountOfArticle(article.getId());

		List<Comment> comments = commentReadPort.readDefaultComments(
			command.getBoardType(),
			article.getId(),
			article.getCreatedBy(),
			command.getUserProvider().getRequestUserId()
		);

		// 비로그인, 로그인, 글 작성자 조회에 따라, 숨겨진 댓글과 답글에 대한 처리
		List<Comment> filteredComments = comments.stream()
			.map(comment -> comment.processSecretComment(
				command.getBoardType(),
				article.getCreatedBy(),
				command.getUserProvider().getRequestUserId())
			).toList();

		List<Long> validCommentIds = filteredComments.stream()
			.filter(Comment::isVisible)
			.map(Comment::getCommentId)
			.toList();

		List<Comment> commentReplies = commentReadPort.readRepliesByCommentId(
			article.getId(),
			validCommentIds
		);

		List<Comment> allComments = Stream.concat(filteredComments.stream(), commentReplies.stream()).toList();
		List<Long> userIds = allComments.stream()
			.filter(comment -> comment.getCreatedBy() != null)
			.distinct()
			.map(Comment::getCreatedBy)
			.toList();

		Map<Long, UserInfo> commentOwner = memberFeignPort.getUserMapByUserIds(userIds, true);
		List<CommentResponse> commentResponses = allComments.stream()
			.map(c -> {
				return CommentResponse.builder()
					.commentId(c.getCommentId())
					.articleId(c.getArticleId())
					.parentId(c.getParentId())
					.content(c.getContent())
					.visible(c.isVisible())
					.createdAt(c.getCreatedAt())
					.createdBy(c.getCreatedBy())
					.userInfo(
						commentOwner.isEmpty() || !commentOwner.containsKey(c.getCreatedBy())
							? null
							: commentOwner.get(c.getCreatedBy())
					)
					.build();
			})
			.toList();

		boolean isLiked = false;
		if (command.getUserProvider().isValidUserId()) {
			UserReactionType userReactionType = userReactionPort.getReactionTypeById(
				UserReactionId.builder()
					.boardType(command.getBoardType())
					.articleId(command.getArticleId())
					.userReactionType(UserReactionType.LIKE)
					.createdBy(command.getUserProvider().getRequestUserId())
					.build()
			);
			isLiked = ObjectUtils.nullSafeEquals(UserReactionType.LIKE, userReactionType);
		}

		return ArticleContentWithCommentResponse.of(
			article,
			articleOwner.get(article.getCreatedBy()),
			images,
			allCommentCount,
			commentResponses,
			isLiked
		);
	}

	@Override
	public Page<ArticleBriefResponse> getArticleIndexList(ArticleIndexListCommand command) {
		Pageable pageable = PageRequest.of(
			command.getPage(),
			command.getOffset(),
			Sort.by(Sort.Direction.DESC, command.getOrderBy()));

		Page<ArticleBriefResponse> pagingArticleList = articleReadPort.retrieveArticleIndexList(
			command.getBoardType(),
			command.getCursor(),
			command.isOnlyPicture(),
			command.getUserId(),
			pageable
		);

		//TODO "pageable"-"pageNumber": 1 등의 값만 사용. 응답값 재구성

		List<Long> articleOwnerIds = pagingArticleList.getContent().stream()
			.map(ArticleBriefResponse::getCreatedBy)
			.distinct()
			.toList();

		Map<Long, UserInfo> userMap = memberFeignPort.getUserMapByUserIds(articleOwnerIds, true);

		for (ArticleBriefResponse articleBriefDto : pagingArticleList.getContent()) {
			articleBriefDto.setUserInfo(userMap.get(articleBriefDto.getCreatedBy()));
		}

		return pagingArticleList;

	}

	//Command
	@Override
	public Long writeArticle(ArticleWriteCommand articleWriteCommand) {
		//TODO 한 사용자는 같은 게시판에 X분 연속으로 글 등록하기가 제한됨. => 서버 캐시 등
		// articleReadPort.getXXXByAuthorId()
		Optional<Board> foundBoard = boardReadPort.findByBoardType(articleWriteCommand.getBoardType());
		Board board = foundBoard.orElseThrow(
			() -> new BuddyMeException(ServiceStatusCode.COMMUNITY_SERVICE, "해당하는 게시판이 없습니다."));
		board.checkPermission(articleWriteCommand);

		//TODO: Article 도메인 객체에서 비즈니스 로직을 처리하며 생성 후 넘겨야 한다.
		Article savedArticle = articleWritePort.writeArticle(articleWriteCommand);

		if (!CollectionUtils.isEmpty(articleWriteCommand.getImages())) {
			int savedImageCount = imageWritePort.saveImages(
				savedArticle.getId(),
				savedArticle.getCreatedBy(),
				savedArticle.getCreatedAt(),
				articleWriteCommand.getImages()
			);

			// savedImageCount 추후 사용자별 이미지 저장 수 제한 정책 시
			if (savedImageCount != articleWriteCommand.getImages().size())
				throw new BuddyMeException(ServiceStatusCode.INTERVAL_SERVER_ERROR, "게시글 이미지 저장에 실패했습니다.");
		}

		return savedArticle.getId();
	}

	@Override
	public Long editArticle(ArticleEditCommand command) {
		Article originalArticle = articleReadPort.readArticle(command.getBoardType(), command.getArticleId(), false);
		originalArticle.checkHasOwnership(command.getUserProvider().getRequestUserId());

		List<ImageResponse> registeredImages = imageReadPort.getImageListByArticle(originalArticle.getId());
		List<ImageResponse> editedImages = command.getImages().stream()
			.sorted(Comparator.comparingInt(ImageInfoCommand::getSortNumber))
			.map(image -> new ImageResponse(image.getSortNumber(), image.getUrl()))
			.toList();

		processArticleImages(originalArticle, registeredImages, editedImages);

		Article changedArticle = originalArticle.copyWithChanges(
			originalArticle,
			command.getTitle(),
			command.getContent(),
			command.isEnableComment()
		);

		return articleEditPort.updateArticle(changedArticle);
	}

	private void processArticleImages(
		Article article,
		List<ImageResponse> registeredImages,
		List<ImageResponse> editedImages
	) {
		/*
		1. 원본에서 'url' 없었던 놈 찾기 -> 등록 대상
		2. 원본에서 'url' 사라진 놈 찾기 -> 삭제 대상
		3. 원본에서 순서만 변경된 것 찾기 -> 수정 대상
		4. 수정본에서 순서 정렬 순서 유효성 검증
		5. CUD 작업 수행 -> Read하여 수정본과 비교하여 일치 여부 검증
		*/

		// 비교를 위한 Map 생성
		Map<String, ImageResponse> registeredImagesMap = registeredImages.stream()
			.collect(Collectors.toMap(ImageResponse::url, image -> image));
		Map<String, ImageResponse> editedImagesMap = editedImages.stream()
			.collect(Collectors.toMap(ImageResponse::url, image -> image));

		// 삭제할 이미지 찾기: 기존 이미지 중 새 이미지 리스트에 없는 것들
		List<String> imageUrlsToDelete = registeredImages.stream()
			.filter(image -> !editedImagesMap.containsKey(image.url()))
			.map(ImageResponse::url)
			.toList();
		List<ImageInfoCommand> imagesToCreate = new ArrayList<>();
		Map<String, ImageResponse> imageMapToUpdate = new HashMap<>();

		// 추가 및 순서 변경 확인
		for (ImageResponse targetImage : editedImages) {
			ImageResponse registeredImage = registeredImagesMap.get(targetImage.url());
			if (registeredImage == null) {
				// 새 이미지 추가
				imagesToCreate.add(
					ImageInfoCommand.builder()
						.sortNumber(targetImage.sortNumber())
						.url(targetImage.url())
						.build()
				);
			} else if (registeredImage.sortNumber() != targetImage.sortNumber()) {
				// 순서가 변경된 경우
				imageMapToUpdate.put(targetImage.url(), targetImage);
			}
		}
		imageWritePort.saveImages(article.getId(), article.getCreatedBy(), article.getCreatedAt(), imagesToCreate);

		if (!imageUrlsToDelete.isEmpty()) {
			memberFeignPort.deleteImageByUrls(imageUrlsToDelete);
			imageDeletePort.deleteAllByArticleIdAndUrlIn(article.getId(), imageUrlsToDelete);
		}

		imageEditPort.editAllImageSortNumber(article.getId(), imageMapToUpdate);
	}

	@Override
	public Long deleteArticle(ArticleRemoveCommand command) {
		// TODO Admin 등 처리?
		boolean isShowAll = false;

		Article article = articleReadPort.readArticle(command.getBoardType(), command.getArticleId(), isShowAll);
		if (Objects.isNull(article)) {
			throw new BuddyMeException(ServiceStatusCode.BAD_REQUEST, "해당하는 게시글이 없습니다.");
		}
		article.checkHasOwnership(command.getUserProvider().getRequestUserId());

		List<ImageResponse> articleImages = imageReadPort.getImageListByArticle(article.getId());

		articleDeletePort.markDeleted(command);
		commentDeletePort.markDeletedByArticleId(article.getId());

		memberFeignPort.deleteImageByUrls(articleImages.stream().map(ImageResponse::url).toList());
		imageDeletePort.deleteAllByArticleId(article.getId());
		return article.getId();
	}

}
