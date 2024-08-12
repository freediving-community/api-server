package com.freediving.communityservice.adapter.out.dto.comment;

import java.time.LocalDateTime;

import com.freediving.communityservice.adapter.out.dto.user.UserInfo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentResponse {
	private final Long commentId;

	private final Long articleId;

	private final Long parentId;

	private final String content;

	private final boolean visible;

	private final LocalDateTime createdAt;

	private final Long createdBy;

	private final UserInfo userInfo;
}
