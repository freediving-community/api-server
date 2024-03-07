package com.freediving.communityservice.application.service;

import org.springframework.stereotype.Service;

import com.freediving.communityservice.application.port.in.UserReactionCommand;
import com.freediving.communityservice.application.port.in.UserReactionUseCase;
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
	private final UserReactionPort userReactionPort;

	@Override
	public int recordUserReaction(UserReactionCommand command) {

		Article targetArticle = articleReadPort.readArticle(command.getBoardType(), command.getArticleId(), false);
		return userReactionPort.addUserReaction(
			command.getUserReactionType(),
			targetArticle.getBoardType(),
			targetArticle.getId(),
			command.getUserProvider()
		);
	}

	@Override
	public int deleteUserReaction(UserReactionCommand command) {
		Article targetArticle = articleReadPort.readArticle(command.getBoardType(), command.getArticleId(), false);
		return userReactionPort.deleteUserReaction(
			command.getUserReactionType(),
			targetArticle.getBoardType(),
			targetArticle.getId(),
			command.getUserProvider()
		);
	}
}
