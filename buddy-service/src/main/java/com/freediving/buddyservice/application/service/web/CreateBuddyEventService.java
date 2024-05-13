package com.freediving.buddyservice.application.service.web;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.freediving.buddyservice.adapter.out.externalservice.FindUser;
import com.freediving.buddyservice.adapter.out.persistence.event.BuddyEventJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.BuddyEventResponseMapper;
import com.freediving.buddyservice.application.port.in.web.command.CreateBuddyEventCommand;
import com.freediving.buddyservice.application.port.in.web.command.CreateBuddyEventUseCase;
import com.freediving.buddyservice.application.port.out.externalservice.query.RequestMemberPort;
import com.freediving.buddyservice.application.port.out.web.CreateBuddyEventPort;
import com.freediving.buddyservice.application.port.out.web.ValidationBuddyEventPort;
import com.freediving.buddyservice.application.port.out.web.command.like.BuddyEventLikeTogglePort;
import com.freediving.buddyservice.domain.command.CreatedBuddyEventResponse;
import com.freediving.buddyservice.domain.enumeration.BuddyEventStatus;
import com.freediving.common.config.annotation.UseCase;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateBuddyEventService implements CreateBuddyEventUseCase {

	private final CreateBuddyEventPort createBuddyEventPort;
	private final ValidationBuddyEventPort validationBuddyEventPort;
	private final BuddyEventResponseMapper buddyEventResponseMapper;
	private final RequestMemberPort requestMemberPort;

	private final BuddyEventLikeTogglePort buddyEventLikeTogglePort;

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public CreatedBuddyEventResponse createBuddyEvent(CreateBuddyEventCommand command) {

		// 1. Member Service로 정상적인 사용자 인지 확인 ( 버디 일정 생성 가능한 사용자? 제재 리스트 사용자? 등. 정상적인 사용자 체크)
		HashMap<Long, FindUser> status = requestMemberPort.getMemberStatus(List.of(command.getUserId()));
		if (status.get(command.getUserId()).getLicenseInfo().getFreeDiving().getRoleLevel() < 0) {
			throw new RuntimeException("비정상적인 사용자."); // TODO 예외 처리
		}

		// 2. 생성한 버디 일정 중에 시간이 겹치는 일정이 있는지 확인.
		if (isValidBuddyEventOverlap(command.getUserId(), command.getEventStartDate(), command.getEventEndDate())
			== false) {
			throw new RuntimeException("버디 일정이 겹칩니다."); // TODO 예외 처리
		}

		// 3. 버디 일정 이벤트 생성하기.
		final BuddyEventJpaEntity createdBuddyEventInfo = createBuddyEventPort.createBuddyEvent(
			CreatedBuddyEventResponse.builder()
				.userId(command.getUserId())
				.eventStartDate(command.getEventStartDate())
				.eventEndDate(command.getEventEndDate())
				.participantCount(command.getParticipantCount())
				.buddyEventConcepts(command.getBuddyEventConcepts())
				.carShareYn(command.getCarShareYn())
				.status(command.getStatus())
				.kakaoRoomCode(command.getKakaoRoomCode())
				.comment(command.getComment())
				.freedivingLevel(command.getFreedivingLevel())
				.divingPools(command.getDivingPools())
				.genderType(command.getGenderType())
				.build());

		// 4. 좋아요 관심 데이터 생성
		buddyEventLikeTogglePort.buddyEventLikeToggleSet(createdBuddyEventInfo);

		return buddyEventResponseMapper.mapToDomainEntity(createdBuddyEventInfo);

	}

	private Boolean isValidBuddyEventOverlap(Long userId, LocalDateTime eventStartTime,
		LocalDateTime eventEndDate) {

		// BuddyEventStatus.RECRUITING, BuddyEventStatus.RECRUITMENT_CLOSED 이벤트 상태들에 대해서
		// 시간이 겹치는 지 확인한다.

		List<String> statusNames = List.of(BuddyEventStatus.RECRUITING, BuddyEventStatus.RECRUITMENT_CLOSED).stream()
			.map(Enum::name)
			.collect(Collectors.toList());

		return validationBuddyEventPort.isValidBuddyEventOverlap(userId, eventStartTime, eventEndDate
			, statusNames);
	}
}
