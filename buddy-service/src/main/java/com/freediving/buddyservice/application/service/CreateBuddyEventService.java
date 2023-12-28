package com.freediving.buddyservice.application.service;

import com.freediving.buddyservice.adapter.out.persistence.BuddyEventsJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.CreatedBuddyEventMapper;
import com.freediving.buddyservice.application.port.in.CreateBuddyEventCommand;
import com.freediving.buddyservice.application.port.in.CreateBuddyEventUseCase;
import com.freediving.buddyservice.application.port.out.CreateBuddyEventPort;
import com.freediving.buddyservice.application.port.out.service.MemberStatus;
import com.freediving.buddyservice.application.port.out.service.RequestMemberPort;
import com.freediving.buddyservice.domain.CreatedBuddyEvent;
import com.freediving.common.config.annotation.UseCase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional
public class CreateBuddyEventService implements CreateBuddyEventUseCase {

	private final CreateBuddyEventPort createBuddyEventPort;
	private final CreatedBuddyEventMapper createdBuddyEventMapper;
	private final RequestMemberPort requestMemberPort;

	@Override
	public CreatedBuddyEvent createBuddyEventV1(CreateBuddyEventCommand command) {

		// 1. Member Serivce로 정상적인 사용자 인지 확인 ( 버디 일정 생성 가능한 사용자? 제재 리스트 사용자? 등. 정상적인 사용자 체크)
		MemberStatus status = requestMemberPort.getMemberStatus(command.getUserId());
		if (status.isValid() == false) {
			throw new RuntimeException("비정상적인 사용자."); // TODO 예외 처리
		}

		// 2. 생성한 버디 일정 중에 시간이 겹치는 일정이 있는지 확인.
		if (isValidBuddyEventOverlap() == false) {
			throw new RuntimeException("버디 일정이 겹칩니다."); // TODO 예외 처리
		}

		// 3. 버디 일정 이벤트 생성하기.
		BuddyEventsJpaEntity createdBuddyEventInfo = createBuddyEventPort.createBuddyEvent(
			CreatedBuddyEvent.builder()
				.userId(command.getUserId())
				.eventStartDate(command.getEventStartDate())
				.eventEndDate(command.getEventEndDate())
				.participantCount(command.getParticipantCount())
				.eventConcepts(command.getEventConcepts())
				.carShareYn(command.getCarShareYn())
				.status(command.getStatus())
				.comment(command.getComment())
				.build());

		return createdBuddyEventMapper.mapToDomainEntity(createdBuddyEventInfo);

	}

	private Boolean isValidBuddyEventOverlap() {
		// true
		return createBuddyEventPort.isValidBuddyEventOverlap();
	}
}
