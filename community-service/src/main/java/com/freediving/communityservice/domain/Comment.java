package com.freediving.communityservice.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.freediving.communityservice.adapter.in.web.UserProvider;

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

	public static List<Comment> getVisibleComments(Long requestUserId, List<Comment> allComments) {
		return allComments.stream()
			.map(c -> {
				if (c.isVisible() || c.getCreatedBy().equals(requestUserId)) {
					return c;
				} else {
					return Comment.builder()
						.commentId(c.getCommentId())
						.articleId(c.getArticleId())
						.parentId(c.getParentId())
						.content("")
						.visible(c.isVisible())
						.createdAt(c.getCreatedAt())
						.createdBy(0L)
						.modifiedAt(c.getModifiedAt())
						.modifiedBy(0L)
						.build();
				}
			}).collect(Collectors.toList());
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

	public void isParentComment() {
		if (this.parentId != null)
			throw new IllegalArgumentException("답글에는 다시 답글을 달 수 없습니다.");
	}

	public void checkCommentOwner(Long requestUserId) {
		if ( ! requestUserId.equals(this.createdBy))
			throw new IllegalArgumentException("수정 권한이 없습니다.");
	}

}
