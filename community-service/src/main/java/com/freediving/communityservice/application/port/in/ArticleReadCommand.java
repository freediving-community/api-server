package com.freediving.communityservice.application.port.in;

import com.freediving.common.SelfValidating;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class ArticleReadCommand extends SelfValidating<ArticleReadCommand> {

	private final Long boardId;
	private final Long articleId;
	private final boolean isEnabledOnly;
	private final boolean withoutComment;

	public ArticleReadCommand(Long boardId, Long articleId, boolean isEnabledOnly, boolean withoutComment) {
		this.boardId = boardId;
		this.articleId = articleId;
		this.isEnabledOnly = isEnabledOnly;
		this.withoutComment = withoutComment;
		this.validateSelf();
	}
}
