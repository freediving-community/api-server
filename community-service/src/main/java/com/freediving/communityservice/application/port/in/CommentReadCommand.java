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
public class CommentReadCommand extends SelfValidating<CommentReadCommand> {

	@NotNull
	private UserProvider requestUser;

	@NotNull
	private BoardType boardType;

	@NotNull
	private Long articleId;

	@NotNull
	private Long commentId;
}
