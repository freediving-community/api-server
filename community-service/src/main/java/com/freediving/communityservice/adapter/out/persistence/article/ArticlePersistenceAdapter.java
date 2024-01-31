package com.freediving.communityservice.adapter.out.persistence.article;

import static com.freediving.communityservice.adapter.out.persistence.article.QArticleJpaEntity.*;
import static com.freediving.communityservice.adapter.out.persistence.comment.QCommentJpaEntity.*;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.communityservice.adapter.out.dto.article.ArticleContent;
import com.freediving.communityservice.application.port.in.ArticleReadCommand;
import com.freediving.communityservice.application.port.in.ArticleWriteCommand;
import com.freediving.communityservice.application.port.out.ArticleReadPort;
import com.freediving.communityservice.application.port.out.ArticleWritePort;
import com.freediving.communityservice.domain.Article;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ArticlePersistenceAdapter implements ArticleWritePort, ArticleReadPort {

	private final ArticleRepository articleRepository;
	private final JPAQueryFactory jpaQueryFactory;
	private final ArticlePersistenceMapper articlePersistenceMapper;

	@Override
	public Article writeArticle(ArticleWriteCommand articleWriteCommand) {
		ArticleJpaEntity savedArticle = articleRepository.save(
			ArticleJpaEntity.of(
				articleWriteCommand.getTitle(),
				articleWriteCommand.getContent(),
				articleWriteCommand.getBoardId(),
				articleWriteCommand.getAuthorName(),
				articleWriteCommand.isEnableComment(),
				articleWriteCommand.getCreatedBy()
			)
		);

		return articlePersistenceMapper.mapToDomain(savedArticle);
	}

	@Override
	public ArticleContent readArticle(ArticleReadCommand articleReadCommand) {

		ArticleJpaEntity foundArticle = jpaQueryFactory
			.select(articleJpaEntity)
			.from(articleJpaEntity)
			.leftJoin(commentJpaEntity).on(articleJpaEntity.articleId.eq(commentJpaEntity.articleId))
			.where(
				boardIdEq(articleReadCommand.getBoardId()),
				articleIdEq(articleReadCommand.getArticleId()),
				articleJpaEntity.enableComment.isTrue(),
				articleJpaEntity.visible.isTrue()
			)
			.fetchFirst();

		return null;
	}

	private BooleanExpression boardIdEq(Long boardId) {
		return articleJpaEntity.articleId.eq(boardId);
	}

	private BooleanExpression articleIdEq(Long articleId) {
		return articleJpaEntity.articleId.eq(articleId);
	}

}
