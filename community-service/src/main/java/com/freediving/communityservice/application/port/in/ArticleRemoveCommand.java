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
public class ArticleRemoveCommand extends SelfValidating<ArticleRemoveCommand> {

	private final UserProvider userProvider;

	@NotNull
	private final Long boardId;

	@NotNull
	private final Long articleId;

	public ArticleRemoveCommand(UserProvider userProvider, Long boardId, Long articleId) {
		this.userProvider = userProvider;
		this.boardId = boardId;
		this.articleId = articleId;
		this.validateSelf();
	}
}
