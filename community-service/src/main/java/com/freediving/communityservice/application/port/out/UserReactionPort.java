package com.freediving.communityservice.application.port.out;

import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
import com.freediving.communityservice.adapter.out.persistence.constant.UserReactionType;
import com.freediving.communityservice.adapter.out.persistence.userreact.UserReactionId;

public interface UserReactionPort {

	UserReactionType getReactionTypeById(UserReactionId userReactionId);

	UserReactionType addUserReaction(UserReactionType userReactionType, BoardType boardType, Long articleId,
		UserProvider userProvider);

	int deleteUserReaction(UserReactionType userReactionType, BoardType boardType, Long articleId,
		UserProvider userProvider);
}
