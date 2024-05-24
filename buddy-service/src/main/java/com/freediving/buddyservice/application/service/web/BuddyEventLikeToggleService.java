package com.freediving.buddyservice.application.service.web;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.freediving.buddyservice.adapter.out.persistence.event.BuddyEventJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.likecount.BuddyEventLikeMappingJpaEntity;
import com.freediving.buddyservice.application.port.in.web.command.like.BuddyEventLikeToggleCommand;
import com.freediving.buddyservice.application.port.in.web.command.like.BuddyEventLikeToggleUseCase;
import com.freediving.buddyservice.application.port.out.web.ValidationBuddyEventPort;
import com.freediving.buddyservice.application.port.out.web.command.like.BuddyEventLikeTogglePort;
import com.freediving.common.config.annotation.UseCase;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class BuddyEventLikeToggleService implements BuddyEventLikeToggleUseCase {

	private final BuddyEventLikeTogglePort buddyEventLikeTogglePort;
	private final ValidationBuddyEventPort validationBuddyEventPort;

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public Integer buddyEventLikeToggle(BuddyEventLikeToggleCommand command) {

		// 1.  버디 이벤트가 어떻게 노출될지 하나하나 판단하기가 힘듬. 이벤트가 실제 존재하는지만 판단한다.
		// todo 버디 이벤트가 존재 하는 이벤트인지 확인
		BuddyEventJpaEntity buddyEvent = validationBuddyEventPort.existBuddyEvent(command.getEventId());

		if (buddyEvent == null)
			throw new BuddyMeException(ServiceStatusCode.INTERVAL_SERVER_ERROR, "BuddyEvent is null"); // todo 응답 코드 필요

		// 2. 버디 이벤트 좋아요 설정이 되어있는지 확인한다.
		BuddyEventLikeMappingJpaEntity buddyEventLikeMappingJpaEntity = buddyEventLikeTogglePort.existBuddyEventLikeMapping(
			buddyEvent, command.getUserId());

		// 3. 좋아요 설정/해지

		// 3-1 좋아요 설정 and 좋아요 설정이 되어있는 경우
		if (command.isLikeStatus() == true && (buddyEventLikeMappingJpaEntity != null
			&& buddyEventLikeMappingJpaEntity.getIsDeleted() == false))
			return -1;

		// 3-2 좋아요 해지 and 좋아요 없는 상태
		if (command.isLikeStatus() == false && (buddyEventLikeMappingJpaEntity == null
			|| buddyEventLikeMappingJpaEntity.getIsDeleted() == true))
			return -1;

		// 3-3 좋아요 설정
		if (command.isLikeStatus() == true) {
			return buddyEventLikeTogglePort.buddyEventLikeToggleOn(buddyEvent, command.getUserId(),
				buddyEventLikeMappingJpaEntity);
		}
		// 3-4 좋아요 해지.
		if (command.isLikeStatus() == false) {
			return buddyEventLikeTogglePort.buddyEventLikeToggleOff(buddyEvent, command.getUserId(),
				buddyEventLikeMappingJpaEntity);
		}
		return -1;

	}

}
