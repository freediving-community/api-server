package com.freediving.communityservice.application.port.in;

import com.freediving.common.SelfValidating;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class CommentReadCommand extends SelfValidating<CommentReadCommand> {

	@NotNull
	private Long requestUserId;

	@NotNull
	private Long commentId;

	@NotNull
	private Long boardId;

	@NotNull
	private Long articleId;
}
