package com.freediving.communityservice.adapter.out.persistence.article;

import org.springframework.stereotype.Component;

import com.freediving.communityservice.domain.Article;

@Component
public class ArticlePersistenceMapper {
	public Article mapToDomain(ArticleJpaEntity articleJpaEntity) {
		return Article.builder()
			.id(articleJpaEntity.getArticleId())
			.boardType(articleJpaEntity.getBoardType())
			.title(articleJpaEntity.getTitle())
			.content(articleJpaEntity.getContent())
			.authorName(articleJpaEntity.getAuthorName())
			.viewCount(articleJpaEntity.getViewCount())
			.likeCount(articleJpaEntity.getLikeCount())
			.enableComment(articleJpaEntity.isEnableComment())
			.visible(articleJpaEntity.isVisible())
			.createdAt(articleJpaEntity.getCreatedAt())
			.createdBy(articleJpaEntity.getCreatedBy())
			.modifiedAt(articleJpaEntity.getModifiedAt())
			.modifiedBy(articleJpaEntity.getModifiedBy())
			.build();
	}
}
