package com.freediving.buddyservice.application.service.web;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.freediving.buddyservice.application.port.in.web.command.like.BuddyEventLikeToggleCommand;
import com.freediving.buddyservice.application.port.in.web.command.like.BuddyEventLikeToggleUseCase;
import com.freediving.buddyservice.application.port.out.web.command.like.BuddyEventLikeTogglePort;
import com.freediving.common.config.annotation.UseCase;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class BuddyEventLikeToggleService implements BuddyEventLikeToggleUseCase {

	private final BuddyEventLikeTogglePort buddyEventLikeTogglePort;

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public void buddyEventLikeToggle(BuddyEventLikeToggleCommand command) {

		// 1.  버디 이벤트가 어떻게 노출될지 하나하나 판단하기가 힘듬. 이벤트가 실제 존재하는지만 판단한다.
		// todo 버디 이벤트가 존재 하는 이벤트인지 확인

		// 2. 버디 이벤트 좋아요 설정이 되어있는지 확인한다.

		// 좋아요 수를 반영 한다.

		// 좋아요 설정
		if (command.isLikeStatus() == true) {
			// 2번에서 이미 좋아요 중이라면 바로 리턴한다.

			// 좋아요 설정
			buddyEventLikeToggleOn();
		} else if (command.isLikeStatus() == false) {
			// 2번에서 이미 좋아요가 되어있지 않다면 바로 리턴한다.

			// 좋아요 해지.
			buddyEventLikeToggleOff();
		}

		return;
	}

	private void buddyEventLikeToggleOff() {
	}

	private void buddyEventLikeToggleOn() {
	}

}
