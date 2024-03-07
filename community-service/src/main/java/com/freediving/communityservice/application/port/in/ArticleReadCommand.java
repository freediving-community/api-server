package com.freediving.communityservice.application.port.in;

import com.freediving.common.SelfValidating;
import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;

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
	private final BoardType boardType;

	@NotNull
	private final Long articleId;

	@NotNull
	private final boolean isShowAll;

	@NotNull
	private final boolean withoutComment;

	public ArticleReadCommand(UserProvider userProvider, BoardType boardType, Long articleId, boolean isShowAll,
		boolean withoutComment) {
		this.userProvider = userProvider;
		this.boardType = boardType;
		this.articleId = articleId;
		this.isShowAll = isShowAll;
		this.withoutComment = withoutComment;
		this.validateSelf();
	}
}
