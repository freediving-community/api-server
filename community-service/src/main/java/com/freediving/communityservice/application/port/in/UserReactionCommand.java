package com.freediving.communityservice.application.port.in;

import com.freediving.common.SelfValidating;
import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
import com.freediving.communityservice.adapter.out.persistence.constant.UserReactionType;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class UserReactionCommand extends SelfValidating<UserReactionCommand> {

	@NotNull
	private final UserProvider userProvider;

	@NotNull
	private final BoardType boardType;

	@NotNull
	private final Long articleId;

	@NotNull
	private final UserReactionType userReactionType;

	public UserReactionCommand(UserProvider userProvider, BoardType boardType, Long articleId,
		UserReactionType userReactionType) {
		this.userProvider = userProvider;
		this.boardType = boardType;
		this.articleId = articleId;
		this.userReactionType = userReactionType;
		this.validateSelf();
	}
}
