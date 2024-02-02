package com.freediving.communityservice.domain;

import java.time.LocalDateTime;

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
}
