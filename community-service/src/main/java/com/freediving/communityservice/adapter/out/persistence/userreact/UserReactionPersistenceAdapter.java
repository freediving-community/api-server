package com.freediving.communityservice.adapter.out.persistence.userreact;

import java.util.Optional;

import org.springframework.dao.DataAccessException;

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
		} catch (DataAccessException e) { // JPA 관련 예외를 더 구체적으로 처리
			// 로깅이나 다른 에러 처리 로직을 추가할 수 있습니다.
			throw new IllegalStateException("데이터베이스 처리 중 문제가 발생했습니다.", e);
		} catch (Exception e) { // 기타 예외 처리
			throw new IllegalStateException("알 수 없는 에러가 발생했습니다.", e);
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

		UserReactionJpaEntity target = foundReaction.orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
		if (!target.getUserReactionId().getUserReactionType().equals(userReactionType)) {
			throw new IllegalArgumentException("해당 데이터가 없습니다.");
		}

		userReactionRepository.deleteById(userReactionId);

		return 1;
	}
}
