package com.freediving.buddyservice.application.service;

import org.springframework.transaction.annotation.Transactional;

import com.freediving.buddyservice.application.port.in.command.like.BuddyEventLikeToggleCommand;
import com.freediving.buddyservice.application.port.in.command.like.BuddyEventLikeToggleUseCase;
import com.freediving.common.config.annotation.UseCase;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional
public class BuddyEventLikeToggleService implements BuddyEventLikeToggleUseCase {


	@Override
	public void toggleBuddyEventLike(BuddyEventLikeToggleCommand command) {
		return ;
	}

}
