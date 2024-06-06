package com.freediving.communityservice.application.service;

import org.springframework.stereotype.Service;

import com.freediving.communityservice.adapter.out.persistence.constant.UserReactionType;
import com.freediving.communityservice.application.port.in.UserReactionCommand;
import com.freediving.communityservice.application.port.in.UserReactionUseCase;
import com.freediving.communityservice.application.port.out.ArticleEditPort;
import com.freediving.communityservice.application.port.out.ArticleReadPort;
import com.freediving.communityservice.application.port.out.UserReactionPort;
import com.freediving.communityservice.domain.Article;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserReactionService implements UserReactionUseCase {

	private final ArticleReadPort articleReadPort;
	private final ArticleEditPort articleEditPort;
	private final UserReactionPort userReactionPort;

	@Override
	public UserReactionType recordUserReaction(UserReactionCommand command) {

		Article targetArticle = articleReadPort.readArticle(command.getBoardType(), command.getArticleId(), false);

		UserReactionType savedReactionType = userReactionPort.addUserReaction(
			command.getUserReactionType(),
			targetArticle.getBoardType(),
			targetArticle.getId(),
			command.getUserProvider()
		);

		if (savedReactionType.equals(UserReactionType.LIKE)) {
			articleEditPort.increaseLikeCount(command.getBoardType(), command.getArticleId());
		}

		return savedReactionType;
	}

	@Override
	public int deleteUserReaction(UserReactionCommand command) {
		Article targetArticle = articleReadPort.readArticle(command.getBoardType(), command.getArticleId(), false);
		int processStatus = userReactionPort.deleteUserReaction(
			command.getUserReactionType(),
			targetArticle.getBoardType(),
			targetArticle.getId(),
			command.getUserProvider()
		);

		if (UserReactionType.LIKE.equals(command.getUserReactionType()) && processStatus == 1) {
			articleEditPort.decreaseLikeCount(command.getBoardType(), command.getArticleId());
		}

		return processStatus;
	}
}
