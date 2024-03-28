package com.freediving.communityservice.application.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.freediving.communityservice.adapter.out.dto.article.ArticleBriefDto;
import com.freediving.communityservice.adapter.out.dto.article.ArticleContent;
import com.freediving.communityservice.adapter.out.dto.article.ArticleContentWithComment;
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
	private final CommentDeletePort commentDeletePort;
	private final UserReactionPort userReactionPort;

	//Query
	// @Override
	// public Article getArticle(ArticleReadCommand command) {
	// 	return articleReadPort.readArticle(command.getBoardType(), command.getArticleId(), command.isShowAll());
	// }

	@Override
	public ArticleContent getArticleWithComment(ArticleReadCommand command) {

		if (command.isWithoutComment()) { // 본문 내용 수정 등
			Article onlyArticle = articleReadPort.readArticle(
				command.getBoardType(),
				command.getArticleId(),
				command.isShowAll()
			);
			return new ArticleContent(onlyArticle);
		}

		ArticleContentWithComment foundContent = articleReadPort.readArticleWithComment(
			command.getBoardType(),
			command.getArticleId(),
			command.isShowAll(),
			command.getUserProvider()
		);

		// 비밀로 설정된 댓글,답글에 대한 처리
		List<Comment> filteredComments = foundContent.getComments().stream()
			.map(comment -> comment.filterComment(
				command.getBoardType(),
				foundContent.getArticle().getCreatedBy(),
				command.getUserProvider().getRequestUserId())
			)
			.collect(Collectors.toList());

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

		return new ArticleContentWithComment(foundContent.getArticle(), filteredComments, isLiked);
	}

	@Override
	public Page<ArticleBriefDto> getArticleIndexList(ArticleIndexListCommand command) {
		Pageable pageable = PageRequest.of(command.getPage(), command.getOffset(),
			Sort.by(Sort.Direction.DESC, command.getOrderBy()));
		Page<ArticleBriefDto> pagingArticleList = articleReadPort.retrieveArticleIndexList(command.getBoardType(),
			command.getCursor(), pageable);

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
		//TODO 한 사용자는 같은 게시판에 00초 내로 글 추가가 제한됨. 프론트에서 먼저 체크 등.
		// articleReadPort.getXXXByAuthorId()
		Optional<Board> foundBoard = boardReadPort.findByBoardType(articleWriteCommand.getBoardType());
		Board board = foundBoard.orElseThrow(() -> new IllegalArgumentException("해당하는 게시판이 없습니다."));
		board.checkPermission(articleWriteCommand);
		Article savedArticle = articleWritePort.writeArticle(articleWriteCommand);
		//TODO articleWriteCommand Hashtag 저장

		return savedArticle.getId();
	}

	@Override
	public Long editArticle(ArticleEditCommand command) {
		Article originalArticle = articleReadPort.readArticle(command.getBoardType(), command.getArticleId(), false);
		originalArticle.checkHasOwnership(command.getUserProvider().getRequestUserId());

		Long updatedArticleId = articleEditPort.updateArticle(command.getBoardType(), command.getArticleId(),
			command.getTitle(), command.getContent(), command.getHashtagIds(), command.isEnableComment());

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

		return article.getId();
	}

}
