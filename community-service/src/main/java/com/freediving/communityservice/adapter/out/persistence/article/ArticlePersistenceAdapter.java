package com.freediving.communityservice.adapter.out.persistence.article;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.communityservice.application.port.in.ArticleWriteCommand;
import com.freediving.communityservice.application.port.out.ArticleReadPort;
import com.freediving.communityservice.application.port.out.ArticleWritePort;
import com.freediving.communityservice.domain.Article;
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
}
