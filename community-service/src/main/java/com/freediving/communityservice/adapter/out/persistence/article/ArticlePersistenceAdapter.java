package com.freediving.communityservice.adapter.out.persistence.article;

import static com.freediving.communityservice.adapter.out.persistence.article.QArticleJpaEntity.*;
import static com.freediving.communityservice.adapter.out.persistence.comment.QCommentJpaEntity.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.dto.article.ArticleBriefDto;
import com.freediving.communityservice.adapter.out.dto.article.ArticleContentWithComment;
import com.freediving.communityservice.adapter.out.persistence.comment.CommentJpaEntity;
import com.freediving.communityservice.adapter.out.persistence.comment.CommentPersistenceMapper;
import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
import com.freediving.communityservice.application.port.in.ArticleRemoveCommand;
import com.freediving.communityservice.application.port.in.ArticleWriteCommand;
import com.freediving.communityservice.application.port.out.ArticleDeletePort;
import com.freediving.communityservice.application.port.out.ArticleEditPort;
import com.freediving.communityservice.application.port.out.ArticleReadPort;
import com.freediving.communityservice.application.port.out.ArticleWritePort;
import com.freediving.communityservice.domain.Article;
import com.freediving.communityservice.domain.Comment;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ArticlePersistenceAdapter
	implements ArticleWritePort, ArticleReadPort, ArticleEditPort, ArticleDeletePort {

	private final ArticleRepository articleRepository;
	private final JPAQueryFactory jpaQueryFactory;
	private final ArticlePersistenceMapper articleMapper;
	private final CommentPersistenceMapper commentMapper;

	@Override
	public Article writeArticle(ArticleWriteCommand articleWriteCommand) {
		ArticleJpaEntity savedArticle = articleRepository.save(
			ArticleJpaEntity.of(
				articleWriteCommand.getTitle(),
				articleWriteCommand.getContent(),
				articleWriteCommand.getBoardType(),
				articleWriteCommand.getAuthorName(),
				articleWriteCommand.isEnableComment()
			)
		);

		return articleMapper.mapToDomain(savedArticle);
	}

	@Override
	public Article readArticle(BoardType boardType, Long articleId, boolean isShowAll) {
		ArticleJpaEntity foundArticle = jpaQueryFactory
			.selectFrom(articleJpaEntity)
			.where(
				boardTypeEq(boardType),
				articleIdEq(articleId),
				isShowAll ?
					null : articleJpaEntity.deletedAt.isNull()
			).fetchOne();

		if (foundArticle == null) {
			throw new IllegalArgumentException("해당하는 게시글이 없습니다.");
		}
		return articleMapper.mapToDomain(foundArticle);
	}

	@Override
	public ArticleContentWithComment readArticleWithComment(BoardType boardType, Long articleId, boolean isShowAll,
		UserProvider requestUser) {

		Article foundArticle = readArticle(boardType, articleId, isShowAll);
		Long articleOwnerId = foundArticle.getCreatedBy();

		/*
		 * 게시글 요청자
		 * 1 = 비로그인 사용자
		 * 2 = 로그인 사용자
		 * 3 = 게시글 작성자
		 * */
		int querySwitchNum = 2;

		if (requestUser.getRequestUserId().equals(-1L)) {
			querySwitchNum = 1;
		} else if (articleOwnerId.equals(requestUser.getRequestUserId())) {
			querySwitchNum = 3;
		}

		List<CommentJpaEntity> articleComments = new ArrayList<CommentJpaEntity>();

		switch (querySwitchNum) {// TODO Native Query 적용 예정
			case 1:
				articleComments = jpaQueryFactory
					.selectFrom(commentJpaEntity)
					.where(
						commentJpaEntity.articleId.eq(foundArticle.getId()),
						commentJpaEntity.deletedAt.isNull(),
						isShowAll ?
							null : commentJpaEntity.visible.isTrue()
					).fetch();
				break;
			case 2:
				articleComments = jpaQueryFactory
					.selectFrom(commentJpaEntity)
					.where(
						commentJpaEntity.articleId.eq(foundArticle.getId()),
						commentJpaEntity.deletedAt.isNull()
					).orderBy(commentJpaEntity.createdAt.desc())
					.fetch();
				break;
		}

		List<Comment> comments = articleComments.stream()
			.map(commentMapper::mapToDomain)
			.toList();
		return new ArticleContentWithComment(foundArticle, comments, false);
	}

	@Override
	public Page<ArticleBriefDto> retrieveArticleIndexList(BoardType boardType, Long cursor, Pageable pageable) {
		List<ArticleBriefDto> articleJpaEntityList = jpaQueryFactory
			.select(
				Projections.bean(ArticleBriefDto.class,
					articleJpaEntity.articleId,
					articleJpaEntity.title,
					articleJpaEntity.authorName,
					articleJpaEntity.createdBy,
					articleJpaEntity.viewCount,
					articleJpaEntity.likeCount,
					articleJpaEntity.commentCount
				)
			)
			.from(articleJpaEntity)
			.where(
				boardTypeEq(boardType),
				articleJpaEntity.deletedAt.isNull()
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(articleSort(pageable))
			.fetch();

		JPAQuery<Long> countQuery = jpaQueryFactory
			.select(articleJpaEntity.count())
			.from(articleJpaEntity)
			.where(
				boardTypeEq(boardType),
				articleJpaEntity.deletedAt.isNull()
			);

		return PageableExecutionUtils.getPage(articleJpaEntityList, pageable, countQuery::fetchOne);

	}

	private OrderSpecifier<?> articleSort(Pageable pageable) {

		if (pageable.getSort().isEmpty()) {
			return null;
		}

		for (Sort.Order order : pageable.getSort()) {
			return switch (order.getProperty()) {
				case "createdAt" -> new OrderSpecifier(order.getDirection().isDescending() ? Order.DESC : Order.ASC,
					articleJpaEntity.createdAt);
				case "liked" -> new OrderSpecifier(order.getDirection().isDescending() ? Order.DESC : Order.ASC,
					articleJpaEntity.likeCount);
				case "comment" -> new OrderSpecifier(order.getDirection().isDescending() ? Order.DESC : Order.ASC,
					articleJpaEntity.commentCount);
				case "hits" -> new OrderSpecifier(order.getDirection().isDescending() ? Order.DESC : Order.ASC,
					articleJpaEntity.viewCount);
				default -> null;
			};
		}

		return null;
	}

	@Override
	public Long updateArticle(BoardType boardType, Long articleId, String title, String content, List<Long> hashtagIds,
		boolean enableComment) {
		ArticleJpaEntity foundArticle = jpaQueryFactory
			.selectFrom(articleJpaEntity)
			.where(
				boardTypeEq(boardType),
				articleIdEq(articleId)
			).fetchOne();

		if (foundArticle == null) {
			throw new IllegalArgumentException("해당하는 게시글이 없습니다.");
		}

		foundArticle.changeArticleContents(title, content, hashtagIds, enableComment);

		return foundArticle.getArticleId();
	}

	@Override
	public int increaseLikeCount(BoardType boardType, Long articleId) {
		return articleRepository.increaseLikeCount(boardType.name(), articleId);
	}

	@Override
	public int decreaseLikeCount(BoardType boardType, Long articleId) {
		return articleRepository.decreaseLikeCount(boardType.name(), articleId);
	}

	@Override
	public int increaseViewCount(BoardType boardType, Long articleId) {
		return articleRepository.increaseViewCount(boardType.name(), articleId);
	}

	@Override
	public Long markDeleted(ArticleRemoveCommand articleRemoveCommand) {

		ArticleJpaEntity articleJpa = articleRepository.findById(articleRemoveCommand.getArticleId())
			.orElseThrow(IllegalStateException::new);
		articleJpa.markDeletedNow();
		return 1L;

	}

	private BooleanExpression boardTypeEq(BoardType boardType) {
		return articleJpaEntity.boardType.eq(boardType);
	}

	private BooleanExpression articleIdEq(Long articleId) {
		return articleJpaEntity.articleId.eq(articleId);
	}
}
