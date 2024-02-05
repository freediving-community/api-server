package com.freediving.communityservice.application.port.in;

import com.freediving.common.SelfValidating;
import com.freediving.communityservice.adapter.in.web.UserProvider;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class ArticleReadCommand extends SelfValidating<ArticleReadCommand> {

	private final UserProvider userProvider;

	@NotNull
	private final Long boardId;

	@NotNull
	private final Long articleId;

	@NotNull
	private final boolean isEnabledOnly;

	@NotNull
	private final boolean withoutComment;

	public ArticleReadCommand(UserProvider userProvider, Long boardId, Long articleId, boolean isEnabledOnly,
		boolean withoutComment) {
		this.userProvider = userProvider;
		this.boardId = boardId;
		this.articleId = articleId;
		this.isEnabledOnly = isEnabledOnly;
		this.withoutComment = withoutComment;
		this.validateSelf();
	}
}
