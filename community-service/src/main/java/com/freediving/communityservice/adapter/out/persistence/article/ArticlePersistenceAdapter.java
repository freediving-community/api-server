package com.freediving.communityservice.adapter.out.persistence.article;

import static com.freediving.communityservice.adapter.out.persistence.article.QArticleJpaEntity.*;
import static com.freediving.communityservice.adapter.out.persistence.comment.QCommentJpaEntity.*;

import java.util.List;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.communityservice.adapter.out.dto.article.ArticleContentWithComment;
import com.freediving.communityservice.adapter.out.persistence.comment.CommentJpaEntity;
import com.freediving.communityservice.adapter.out.persistence.comment.CommentPersistenceMapper;
import com.freediving.communityservice.application.port.in.ArticleReadCommand;
import com.freediving.communityservice.application.port.in.ArticleWriteCommand;
import com.freediving.communityservice.application.port.out.ArticleReadPort;
import com.freediving.communityservice.application.port.out.ArticleWritePort;
import com.freediving.communityservice.domain.Article;
import com.freediving.communityservice.domain.Comment;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ArticlePersistenceAdapter implements ArticleWritePort, ArticleReadPort {

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
				articleWriteCommand.getBoardId(),
				articleWriteCommand.getAuthorName(),
				articleWriteCommand.isEnableComment(),
				articleWriteCommand.getUserProvider().getRequestUserId()
			)
		);

		return articleMapper.mapToDomain(savedArticle);
	}

	@Override
	public Article readArticle(ArticleReadCommand articleReadCommand) {
		ArticleJpaEntity foundArticle = jpaQueryFactory
			.selectFrom(articleJpaEntity)
			.where(
				boardIdEq(articleReadCommand.getBoardId()),
				articleIdEq(articleReadCommand.getArticleId()),
				articleJpaEntity.enableComment.isTrue(),
				articleJpaEntity.visible.isTrue()
			).fetchOne();

		if (foundArticle == null) {
			throw new IllegalArgumentException("해당하는 게시글이 없습니다.");
		}
		return articleMapper.mapToDomain(foundArticle);
	}

	@Override
	public ArticleContentWithComment readArticleWithComment(ArticleReadCommand articleReadCommand) {

		Article foundArticle = readArticle(articleReadCommand);

		List<CommentJpaEntity> articleComments = jpaQueryFactory
			.selectFrom(commentJpaEntity)
			.where(
				commentJpaEntity.articleId.eq(foundArticle.getId()),
				commentJpaEntity.visible.isTrue()
			).fetch();

		Article article = readArticle(articleReadCommand);
		List<Comment> comments = articleComments.stream()
			.map(commentMapper::mapToDomain)
			.toList();
		return new ArticleContentWithComment(article, comments);

		/*ArticleContentWithComment foundArticle = jpaQueryFactory
			.select(
				new QArticleContentWithComment(
				articleJpaEntity.articleId,
				articleJpaEntity.title,
				articleJpaEntity.content,
				articleJpaEntity.authorName,
				articleJpaEntity.viewCount,
				articleJpaEntity.likeCount,
				articleJpaEntity.enableComment,
				articleJpaEntity.createdAt,
				articleJpaEntity.createdBy,
				articleJpaEntity.modifiedAt,
				articleJpaEntity.modifiedBy,
				commentJpaEntity.commentId,
				commentJpaEntity.parentId,
				commentJpaEntity.content,
				commentJpaEntity.visible,
				commentJpaEntity.createdAt,
				commentJpaEntity.createdBy,
				commentJpaEntity.modifiedAt,
				commentJpaEntity.modifiedBy
				)
			)
			.from(articleJpaEntity)
			.leftJoin(commentJpaEntity).on(articleJpaEntity.articleId.eq(commentJpaEntity.articleId))
			.where(
				boardIdEq(articleReadCommand.getBoardId()),
				articleIdEq(articleReadCommand.getArticleId()),
				articleJpaEntity.enableComment.isTrue(),
				articleJpaEntity.visible.isTrue()
			)
			.fetchOne();
		// articlePersistenceMapper.mapToDomain(foundArticle);
		return foundArticle;*/
	}

	private BooleanExpression boardIdEq(Long boardId) {
		return articleJpaEntity.articleId.eq(boardId);
	}

	private BooleanExpression articleIdEq(Long articleId) {
		return articleJpaEntity.articleId.eq(articleId);
	}

}
