package com.freediving.communityservice.domain;

import java.time.LocalDateTime;

import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class Article {

	private final Long id;

	private final BoardType boardType;

	private final String title;

	private final String content;

	@Setter
	private int viewCount;

	@Setter
	private int likeCount;

	@Setter
	private int commentCount;

	private final boolean enableComment;

	private final LocalDateTime deletedAt;

	private final LocalDateTime createdAt;

	private final Long createdBy;

	private final LocalDateTime modifiedAt;

	private final Long modifiedBy;

	public void checkCommentEnabled() {
		if (!this.enableComment)
			throw new BuddyMeException(ServiceStatusCode.BAD_REQUEST, "댓글을 작성할 수 없는 게시물입니다.");
	}

	public void checkHasOwnership(Long requestUserId) {
		if (!this.createdBy.equals(requestUserId))
			throw new BuddyMeException(ServiceStatusCode.FORBIDDEN, "권한이 없습니다.");
	}

	public Article copyWithChanges(Article originalArticle, String title, String content, boolean enableComment) {
		return Article.builder()
			.id(originalArticle.getId())
			.boardType(originalArticle.getBoardType())
			.title(title)
			.content(content)
			.viewCount(originalArticle.getViewCount())
			.likeCount(originalArticle.getLikeCount())
			.commentCount(originalArticle.getCommentCount())
			.enableComment(enableComment)
			.createdAt(originalArticle.getCreatedAt())
			.createdBy(originalArticle.getCreatedBy())
			.modifiedAt(LocalDateTime.now())
			.modifiedBy(originalArticle.getModifiedBy())
			.build();
	}

	public void increaseViewCount() {
		setViewCount(this.viewCount + 1);
	}

	public void increaseLikeCount() {
		setLikeCount(this.likeCount + 1);
	}

	public void decreaseLikeCount() {
		setLikeCount(this.likeCount - 1);
	}

	public void increaseCommentCount() {
		setCommentCount(this.commentCount + 1);
	}

	public void decreaseCommentCount() {
		setCommentCount(this.commentCount - 1);
	}
}
