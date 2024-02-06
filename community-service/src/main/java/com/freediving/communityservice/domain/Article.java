package com.freediving.communityservice.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Article {
	private final Long id;

	private final Long boardId;

	private final String title;

	private final String content;

	private final String authorName;

	private final int viewCount;

	private final int likeCount;

	private final boolean enableComment;

	private final boolean visible;

	private final LocalDateTime createdAt;

	private final Long createdBy;

	private final LocalDateTime modifiedAt;

	private final Long modifiedBy;

	public void canCreateComment() {
		if( ! this.enableComment )
			throw new IllegalArgumentException("댓글을 작성할 수 없는 게시물입니다.");
	}
}
