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
public class CommentDeleteCommand extends SelfValidating<CommentDeleteCommand> {

	@NotNull
	private UserProvider requestUser;

	@NotNull
	private final BoardType boardType;

	@NotNull
	private final Long articleId;

	private final Long parentId;

	@NotNull
	private final Long commentId;

	public CommentDeleteCommand(UserProvider requestUser, BoardType boardType, Long articleId, Long parentId,
		Long commentId) {
		this.requestUser = requestUser;
		this.boardType = boardType;
		this.articleId = articleId;
		this.parentId = parentId;
		this.commentId = commentId;
		this.validateSelf();
	}

	public boolean hasParentComment() {
		return this.parentId != null;
	}
}
