package com.freediving.communityservice.application.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.freediving.communityservice.adapter.out.dto.article.ArticleBriefDto;
import com.freediving.communityservice.adapter.out.dto.article.ArticleContent;
import com.freediving.communityservice.adapter.out.dto.article.ArticleContentWithComment;
import com.freediving.communityservice.adapter.out.dto.image.ImageResponse;
import com.freediving.communityservice.adapter.out.persistence.constant.UserReactionType;
import com.freediving.communityservice.adapter.out.persistence.userreact.UserReactionId;
import com.freediving.communityservice.application.port.in.ArticleEditCommand;
import com.freediving.communityservice.application.port.in.ArticleIndexListCommand;
import com.freediving.communityservice.application.port.in.ArticleReadCommand;
import com.freediving.communityservice.application.port.in.ArticleRemoveCommand;
import com.freediving.communityservice.application.port.in.ArticleUseCase;
import com.freediving.communityservice.application.port.in.ArticleWriteCommand;
import com.freediving.communityservice.application.port.out.ArticleDeletePort;
import com.freediving.communityservice.application.port.out.ArticleEditPort;
import com.freediving.communityservice.application.port.out.ArticleReadPort;
import com.freediving.communityservice.application.port.out.ArticleWritePort;
import com.freediving.communityservice.application.port.out.BoardReadPort;
import com.freediving.communityservice.application.port.out.CommentDeletePort;
import com.freediving.communityservice.application.port.out.CommentReadPort;
import com.freediving.communityservice.application.port.out.ImageDeletePort;
import com.freediving.communityservice.application.port.out.ImageReadPort;
import com.freediving.communityservice.application.port.out.ImageWritePort;
import com.freediving.communityservice.application.port.out.UserReactionPort;
import com.freediving.communityservice.domain.Article;
import com.freediving.communityservice.domain.Board;
import com.freediving.communityservice.domain.Comment;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

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
	// private final ImageEditPort imageEditPort;
	private final ImageDeletePort imageDeletePort;

	//Query
	// @Override
	// public Article getArticle(ArticleReadCommand command) {
	// 	return articleReadPort.readArticle(command.getBoardType(), command.getArticleId(), command.isShowAll());
	// }

	@Override
	public ArticleContent getArticleWithComment(ArticleReadCommand command) {

		Article article = articleReadPort.readArticle(
			command.getBoardType(),
			command.getArticleId(),
			command.isShowAll()
		);
		List<ImageResponse> images = imageReadPort.getImageListByArticle(article.getId());

		if (command.isWithoutComment()) { // 본문 내용 수정 등
			return new ArticleContent(article, images);
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
			)
			.toList();

		List<Long> validCommentIds = filteredComments.stream()
			.filter(Comment::isVisible)
			.map(Comment::getCommentId)
			.toList();

		List<Comment> commentReplies = commentReadPort.readRepliesByCommentId(
			article.getId(),
			validCommentIds
		);

		boolean isLiked = false;
		if (command.getUserProvider().getRequestUserId() != null) {
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

		return new ArticleContentWithComment(
			article,
			images,
			Stream.concat(filteredComments.stream(), commentReplies.stream()).toList(),
			isLiked,
			allCommentCount
		);
	}

	@Override
	public Page<ArticleBriefDto> getArticleIndexList(ArticleIndexListCommand command) {
		Pageable pageable = PageRequest.of(
			command.getPage(),
			command.getOffset(),
			Sort.by(Sort.Direction.DESC, command.getOrderBy()));

		Page<ArticleBriefDto> pagingArticleList = articleReadPort.retrieveArticleIndexList(
			command.getBoardType(),
			command.getCursor(),
			command.isOnlyPicture(),
			pageable
		);

		//TODO ArticleBriefDto pagingArticleList.getContent() 멤버 서비스 : 사용자 상태 및 profileImg 요청
		//TODO "pageable"-"pageNumber": 1 등의 값만 사용. 응답값 재구성

		List<Long> articleOwnerIds = pagingArticleList.getContent().stream()
			.map(ArticleBriefDto::getCreatedBy)
			.toList();

		/*
		 * memberClient.getMemberInfo( articleOwnerIds );
		 *
		 * */

		return pagingArticleList;

	}

	//Command
	@Override
	public Long writeArticle(ArticleWriteCommand articleWriteCommand) {
		//TODO 한 사용자는 같은 게시판에 X분 연속으로 글 등록하기가 제한됨. => 서버 캐시 등
		// articleReadPort.getXXXByAuthorId()
		Optional<Board> foundBoard = boardReadPort.findByBoardType(articleWriteCommand.getBoardType());
		Board board = foundBoard.orElseThrow(() -> new IllegalArgumentException("해당하는 게시판이 없습니다."));
		board.checkPermission(articleWriteCommand);

		//TODO: Article 도메인 객체에서 비즈니스 로직을 처리하며 생성 후 넘겨야 한다.
		Article savedArticle = articleWritePort.writeArticle(articleWriteCommand);

		if (!CollectionUtils.isEmpty(articleWriteCommand.getImages())) {
			int savedImageCount = imageWritePort.saveImages(savedArticle, articleWriteCommand.getImages());

			// savedImageCount 추후 사용자별 이미지 저장 수 제한 정책 시
			if (savedImageCount != articleWriteCommand.getImages().size())
				throw new RuntimeException("게시글 이미지 저장에 실패했습니다.");
		}

		return savedArticle.getId();
	}

	@Override
	public Long editArticle(ArticleEditCommand command) {
		Article originalArticle = articleReadPort.readArticle(command.getBoardType(), command.getArticleId(), false);
		originalArticle.checkHasOwnership(command.getUserProvider().getRequestUserId());

		Article changedArticle = originalArticle.copyWithChanges(
			originalArticle,
			command.getTitle(),
			command.getContent(),
			command.isEnableComment()
		);

		Long updatedArticleId = articleEditPort.updateArticle(changedArticle);

		return null;
	}

	@Override
	public Long deleteArticle(ArticleRemoveCommand command) {
		// TODO Admin 등 처리?
		boolean isShowAll = false;

		Article article = articleReadPort.readArticle(command.getBoardType(), command.getArticleId(), isShowAll);
		if (Objects.isNull(article)) {
			throw new IllegalArgumentException("해당하는 게시글이 없습니다.");
		}
		article.checkHasOwnership(command.getUserProvider().getRequestUserId());

		articleDeletePort.markDeleted(command);
		commentDeletePort.markDeleted(article.getId());
		imageDeletePort.deleteAllByArticleId(article.getId());

		return article.getId();
	}

}
