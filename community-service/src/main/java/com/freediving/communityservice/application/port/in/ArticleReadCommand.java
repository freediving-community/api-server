package com.freediving.communityservice.application.port.in;

import com.freediving.common.SelfValidating;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class ArticleReadCommand extends SelfValidating<ArticleReadCommand> {

	private final boolean isEnabledOnly;
	private final boolean withComment;

	public ArticleReadCommand(boolean isEnabledOnly, boolean withComment) {
		this.isEnabledOnly = isEnabledOnly;
		this.withComment = withComment;
		this.validateSelf();
	}
}
