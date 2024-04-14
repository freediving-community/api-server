package com.freediving.communityservice.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Article {

	private final Long id;

	private final BoardType boardType;

	private final String title;

	private final String content;

	private final String authorName;

	private final int viewCount;

	private final int likeCount;

	private final boolean enableComment;

	@JsonIgnore
	private final LocalDateTime deletedAt;

	private final LocalDateTime createdAt;

	private final Long createdBy;

	@JsonIgnore
	private final LocalDateTime modifiedAt;

	@JsonIgnore
	private final Long modifiedBy;

	public void canCreateComment() {
		if (!this.enableComment)
			throw new IllegalArgumentException("댓글을 작성할 수 없는 게시물입니다.");
	}

	public void checkHasOwnership(Long requestUserId) {
		if (!this.createdBy.equals(requestUserId))
			throw new IllegalArgumentException("권한이 없습니다.");
	}

	public Article copyWithChanges(Article originalArticle, String title, String content, boolean enableComment) {
		return Article.builder()
			.id(originalArticle.getId())
			.boardType(originalArticle.getBoardType())
			.title(title)
			.content(content)
			.authorName(originalArticle.getAuthorName())
			.viewCount(originalArticle.getViewCount())
			.likeCount(originalArticle.getLikeCount())
			.enableComment(enableComment)
			.createdAt(originalArticle.getCreatedAt())
			.createdBy(originalArticle.getCreatedBy())
			.modifiedAt(LocalDateTime.now())
			.modifiedBy(originalArticle.getModifiedBy())
			.build();
	}

}
