package com.freediving.communityservice.adapter.out.persistence.article;

import static com.freediving.communityservice.adapter.out.persistence.article.QArticleJpaEntity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("articleId", articleId);

		StringBuffer sqlString = new StringBuffer("""
			WITH ARTICLE_COMMENT AS ( 
					SELECT
						*
					FROM COMMENT
						WHERE ARTICLE_ID = """+articleId+"""
					AND DELETED_AT IS NULL
				ORDER BY CREATED_AT DESC
			)
			, ROOT_COMMENT AS (
				SELECT *
				FROM (
					SELECT
						*
					FROM ARTICLE_COMMENT
					WHERE PARENT_ID IS NULL
			""");

		// 로그인 사용자인 경우
		if( ! requestUser.getRequestUserId().equals(-1L)) { //TODO 비로그인 사용자 공통 영역 추후 정의 후 수정
			paramMap.put("requestUserId", requestUser.getRequestUserId());
			sqlString.append("""
				AND CREATED_BY = """+requestUser.getRequestUserId()+"""
			""");
		}
		sqlString.append("""
				)
			""");

		// 로그인 사용자인 경우
		if( ! requestUser.getRequestUserId().equals(-1L)) { //TODO 비로그인 사용자 공통 영역 추후 정의 후 수정
			sqlString.append("""
				UNION ALL
				SELECT
				*
				FROM ARTICLE_COMMENT
				WHERE PARENT_ID IS NULL
				AND CREATED_BY <> """+requestUser.getRequestUserId()+"""
				""");
		}
		sqlString.append("""
					LIMIT 15
				)
			SELECT *
			FROM ROOT_COMMENT
			UNION ALL
			SELECT *
			FROM ARTICLE_COMMENT
			WHERE PARENT_ID IN ( SELECT COMMENT_ID FROM ROOT_COMMENT )
			""");

		// 비로그인 요청 시, 숨긴 댓글의 답글 불필요
		if( requestUser.getRequestUserId().equals(-1L)) {
			sqlString.append("""
					MINUS
					SELECT *
					FROM ARTICLE_COMMENT
					WHERE VISIBLE = FALSE
					AND PARENT_ID IS NOT NULL
				""");
		}

		List<CommentJpaEntity> articleComments = jdbcClient
				.sql(sqlString.toString())
				.params(paramMap)
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
