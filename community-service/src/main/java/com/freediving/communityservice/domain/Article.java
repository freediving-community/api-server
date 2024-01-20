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

	// private final List<Comment> comments;

	private final LocalDateTime createdAt;

	private final Long createdBy;

	private final LocalDateTime modifiedAt;

	private final Long modifiedBy;

}
