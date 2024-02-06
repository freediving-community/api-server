package com.freediving.communityservice.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

	public void canCreateReplyComment() {
		if( ! this.isVisible() )
			throw new IllegalArgumentException("숨긴 댓글에는 답글을 작성할 수 없습니다.");
	}

}
