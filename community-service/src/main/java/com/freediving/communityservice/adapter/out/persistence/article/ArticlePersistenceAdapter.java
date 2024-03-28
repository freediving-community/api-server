package com.freediving.communityservice.adapter.out.persistence.article;

import static com.freediving.communityservice.adapter.out.persistence.article.QArticleJpaEntity.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.jdbc.core.simple.JdbcClient;

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
	private final JdbcClient jdbcClient;

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

		// 게시글에 해당하는 댓글
		StringBuffer sql = new StringBuffer("""
			WITH ARTICLE_COMMENT AS (
			    SELECT *
			    FROM COMMENT
			    WHERE article_id = :articleId
			      AND DELETED_AT IS NULL
			    ORDER BY CREATED_AT DESC
			)
				SELECT * FROM ARTICLE_COMMENT WHERE PARENT_ID IS NULL 
			""");
		// 로그인 사용자의 댓글 먼저 추출 후 일반 댓글
		if (!requestUser.getRequestUserId().equals(-1L)) {
			sql.append("""
				AND CREATED_BY = :requestUserId
				UNION ALL
				SELECT * FROM ARTICLE_COMMENT WHERE PARENT_ID IS NULL AND CREATED_BY <> :requestUserId""");
		}
		// 추출 댓글 순서에 따른 답글
		sql.append("""
				UNION ALL
				SELECT *
				FROM ARTICLE_COMMENT WHERE PARENT_ID IN (
				SELECT COMMENT_ID FROM ARTICLE_COMMENT WHERE PARENT_ID IS NULL
			""");
		// 로그인 사용자 댓글에 대한 답글 먼저 추출
		if (!requestUser.getRequestUserId().equals(-1L)) {
			sql.append("""
				AND CREATED_BY = :requestUserId
				UNION ALL
				SELECT COMMENT_ID FROM ARTICLE_COMMENT WHERE PARENT_ID IS NULL AND CREATED_BY <> :requestUserId
				""");
		}
		sql.append(")");

		/*
		 * 글 작성자 1111 : 비밀글도 상관없이 전부 필요함.
		 * -1, 2222, 3333 그 외 사용자 => ARTICLE_ID : 다른 사람의 비밀 댓글에 / 달린 답글 제거.
		 * */
		if (!foundArticle.getCreatedBy().equals(requestUser.getRequestUserId())) {
			sql.append("""
								MINUS
								SELECT * FROM ARTICLE_COMMENT 
								WHERE PARENT_ID IN (
									SELECT COMMENT_ID FROM ARTICLE_COMMENT
									WHERE PARENT_ID IS NULL
									AND VISIBLE = false 
									AND CREATED_BY <> :requestUserId
								) 
				""");
		}

		List<CommentJpaEntity> articleComments = jdbcClient
			.sql(sql.toString())
			.param("articleId", articleId)
			.param("requestUserId", requestUser.getRequestUserId())
			.query(CommentJpaEntity.class)
			.list();

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
