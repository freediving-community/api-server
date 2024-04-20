package com.freediving.communityservice.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.util.ObjectUtils;

import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Comment {
	//TODO hasChild 추가 필요.
	private final Long commentId;

	private final Long articleId;

	private final Long parentId;

	private final String content;

	private final boolean visible;

	private final LocalDateTime createdAt;

	private final Long createdBy;

	private final LocalDateTime modifiedAt;

	private final Long modifiedBy;

	public Comment processSecretComment(BoardType boardType, Long articleOwnerId, Long requestUserId) {

		// TODO BoardType에 따른 댓글 분류
		switch (boardType) {
			// 스토리(자유게시판), 버디모집글 QNA
			case BoardType.GENERAL, BoardType.BUDDY_QNA -> {
				if (this.visible)
					return this;
				if (articleOwnerId.equals(requestUserId))
					return this;
				if (this.createdBy.equals(requestUserId))
					return this;
				// 쿼리에서 다른 사용자의 비밀글에 대한 답글은 제거됨.
				if (this.parentId == null)
					return toSecretComment(this);
			}
		}
		return null;
	}

	private Comment toSecretComment(Comment comment) {
		return Comment.builder()
			.commentId(comment.commentId)
			.articleId(comment.getArticleId())
			.parentId(comment.parentId)
			.content(null)
			.visible(comment.isVisible())
			.createdAt(comment.getCreatedAt())
			.createdBy(null)
			.modifiedAt(null)
			.modifiedBy(null)
			.build();
	}

	public void checkCommentVisible(UserProvider requestUser, Long parentCommentCreatedBy, Long articleCreatedBy) {
		if (!this.isVisible()) {
			if (Objects.equals(requestUser.getRequestUserId(), parentCommentCreatedBy))
				return;
			if (Objects.equals(requestUser.getRequestUserId(), articleCreatedBy))
				return;

			throw new IllegalArgumentException("숨긴 댓글에 권한이 없습니다.");
		}
	}

	public void checkParentComment() {
		if (this.parentId != null)
			throw new IllegalArgumentException("답글에는 다시 답글을 달 수 없습니다.");
	}

	public void checkCommentOwner(Long requestUserId) {
		if (!requestUserId.equals(this.createdBy))
			throw new IllegalArgumentException("수정 권한이 없습니다.");
	}

	public boolean isRootComment() {
		return !ObjectUtils.isEmpty(this.parentId);
	}

}
