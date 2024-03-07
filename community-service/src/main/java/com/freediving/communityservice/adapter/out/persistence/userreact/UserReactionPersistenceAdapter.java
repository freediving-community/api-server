package com.freediving.communityservice.adapter.out.persistence.userreact;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
import com.freediving.communityservice.adapter.out.persistence.constant.UserReactionType;
import com.freediving.communityservice.application.port.out.UserReactionPort;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserReactionPersistenceAdapter implements UserReactionPort {

	private final UserReactionRepository userReactionRepository;

	@Override
	public int addUserReaction(UserReactionType userReactionType, BoardType boardType, Long articleId,
		UserProvider userProvider) {
		UserReactionJpaEntity savedReaction = userReactionRepository.save(
			UserReactionJpaEntity.of(userReactionType, boardType, articleId)
		);
		return 1;
	}

	@Override
	public int deleteUserReaction(UserReactionType userReactionType, BoardType boardType, Long articleId,
		UserProvider userProvider) {
		return 1;
	}
}
