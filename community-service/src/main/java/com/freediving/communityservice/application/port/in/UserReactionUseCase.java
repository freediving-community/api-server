package com.freediving.communityservice.application.port.in;

import com.freediving.communityservice.adapter.out.persistence.constant.UserReactionType;

public interface UserReactionUseCase {

	UserReactionType recordUserReaction(UserReactionCommand userReactionCommand);

	int deleteUserReaction(UserReactionCommand userReactionCommand);
}
