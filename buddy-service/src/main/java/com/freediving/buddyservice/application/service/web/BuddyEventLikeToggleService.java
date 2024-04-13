package com.freediving.buddyservice.application.service.web;

import org.springframework.transaction.annotation.Transactional;

import com.freediving.buddyservice.application.port.in.web.command.like.BuddyEventLikeToggleCommand;
import com.freediving.buddyservice.application.port.in.web.command.like.BuddyEventLikeToggleUseCase;
import com.freediving.buddyservice.application.port.out.kafka.SendBuddyEventLikeToggleTaskPort;
import com.freediving.buddyservice.common.task.domain.BuddyEventLikeToggleTask;
import com.freediving.common.config.annotation.UseCase;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional
public class BuddyEventLikeToggleService implements BuddyEventLikeToggleUseCase {

	private final SendBuddyEventLikeToggleTaskPort sendBuddyEventLikeToggleTaskPort;

	@Override
	public void toggleBuddyEventLike(BuddyEventLikeToggleCommand command) {

		// todo 버디 이벤트가 현재 모집 중인 상태인지?, 유효한 버디 이벤트 인지 판단!

		// 카프카 consumer 전송
		BuddyEventLikeToggleTask task = BuddyEventLikeToggleTask.builder().userId(command.getUserId())
			.eventId(command.getEventId()).likeStatus(command.isLikeStatus()).build();

		sendBuddyEventLikeToggleTaskPort.sendRechargingMoneyTaskPort(task);

		return;
	}

}
