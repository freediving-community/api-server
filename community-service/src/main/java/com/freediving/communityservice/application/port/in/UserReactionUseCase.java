package com.freediving.communityservice.application.port.in;

public interface UserReactionUseCase {

	int recordUserReaction(UserReactionCommand userReactionCommand);

	int deleteUserReaction(UserReactionCommand userReactionCommand);
}
