package com.freediving.communityservice.adapter.out.persistence.comment;

import org.springframework.stereotype.Component;

import com.freediving.communityservice.domain.Comment;

@Component
public class CommentPersistenceMapper {
	public Comment mapToDomain(CommentJpaEntity commentJpaEntity) {
		return Comment.builder()
			.commentId(commentJpaEntity.getCommentId())
			.articleId(commentJpaEntity.getArticleId())
			.parentId(commentJpaEntity.getParentId())
			.content(commentJpaEntity.getContent())
			.visible(commentJpaEntity.isVisible())
			.createdAt(commentJpaEntity.getCreatedAt())
			.createdBy(commentJpaEntity.getCreatedBy())
			.modifiedAt(commentJpaEntity.getModifiedAt())
			.modifiedBy(commentJpaEntity.getModifiedBy())
			.build();
	}
}
