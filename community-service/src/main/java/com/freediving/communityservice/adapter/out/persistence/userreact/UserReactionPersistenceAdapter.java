package com.freediving.communityservice.adapter.out.persistence.userreact;

import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;
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
	public UserReactionType getReactionTypeById(UserReactionId userReactionId) {
		Optional<UserReactionJpaEntity> foundReactionType = userReactionRepository.findById(userReactionId);

		return foundReactionType.map(reaction -> {
			return reaction.getUserReactionId().getUserReactionType();
		}).orElse(null);
	}

	@Override
	public UserReactionType addUserReaction(UserReactionType userReactionType, BoardType boardType, Long articleId,
		UserProvider userProvider) {
		try {
			UserReactionJpaEntity savedReaction = userReactionRepository.saveAndFlush(
				UserReactionJpaEntity.of(userReactionType, boardType, articleId, userProvider.getRequestUserId())
			);
			return savedReaction.getUserReactionId().getUserReactionType();
		} catch (DataAccessException e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				throw new BuddyMeException(ServiceStatusCode.BAD_REQUEST, "이미 처리되었습니다.");
			}
			throw new BuddyMeException(ServiceStatusCode.INTERVAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new BuddyMeException(ServiceStatusCode.INTERVAL_SERVER_ERROR);
		}
	}

	@Override
	public int deleteUserReaction(UserReactionType userReactionType, BoardType boardType, Long articleId,
		UserProvider userProvider) {
		UserReactionId userReactionId = UserReactionId.builder()
			.boardType(boardType)
			.articleId(articleId)
			.userReactionType(userReactionType)
			.createdBy(userProvider.getRequestUserId())
			.build();
		Optional<UserReactionJpaEntity> foundReaction = userReactionRepository.findById(userReactionId);

		UserReactionJpaEntity target = foundReaction.orElseThrow(
			() -> new BuddyMeException(ServiceStatusCode.BAD_REQUEST, "해당하는 데이터가 없습니다."));
		if (!target.getUserReactionId().getUserReactionType().equals(userReactionType)) {
			throw new BuddyMeException(ServiceStatusCode.BAD_REQUEST, "해당하는 데이터가 없습니다.");
		}

		userReactionRepository.deleteById(userReactionId);

		return 1;
	}
}
