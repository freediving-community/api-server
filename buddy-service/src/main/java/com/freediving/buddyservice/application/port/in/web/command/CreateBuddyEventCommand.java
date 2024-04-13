package com.freediving.buddyservice.application.port.in.web.command;

import java.time.LocalDateTime;
import java.util.Set;

import com.freediving.buddyservice.common.enumeration.BuddyEventConcept;
import com.freediving.buddyservice.common.enumeration.BuddyEventStatus;
import com.freediving.common.SelfValidating;
import com.freediving.common.enumerate.DivingPool;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * CreateBuddyEventCommand 서비스 레이어로 전달하는 명령 객체
 * 주로 서비스 비즈니스 유효성 체크를 진행한다.
 * 비즈니스 규칙 검증: 예를 들어, 특정 이벤트에 참여할 수 있는 최대 인원수가 정해져 있을 경우, 요청된 인원수가 이 규칙을 위반하지 않는지 검증합니다.
 * 상태 검증: 엔티티의 현재 상태가 특정 작업을 수행하기에 적합한지 검증합니다. 예를 들어, 이미 종료된 이벤트에 대한 참여 요청을 거부할 수 있습니다.
 * 의존성 검증: 요청 처리에 필요한 다른 엔티티나 자원이 존재하고 올바른 상태인지 검증합니다.
 *
 * @author 준희조
 * @version 1.0.0
 * 작성일 2024-02-29
 **/
@EqualsAndHashCode(callSuper = false)
@Getter
public class CreateBuddyEventCommand extends SelfValidating<CreateBuddyEventCommand> {

	@NotNull
	private final Long userId;

	@FutureOrPresent(message = "일정 시작 시간은 현재 시간 이후여야 합니다.")
	private final LocalDateTime eventStartDate;

	@FutureOrPresent(message = "일정 종료 시간은 현재 시간 이후여야 합니다.")
	private final LocalDateTime eventEndDate;

	@Min(value = 1, message = "참여자 수는 최소 1명부터 입니다.") //TODO 참여자 수 최솟값 테스트 케이스 생성.
	@Max(value = 5L, message = "최대 모집 인원은 5명까지 가능합니다.")
	private final Integer participantCount;

	private final Set<BuddyEventConcept> buddyEventConcepts;

	private final Boolean carShareYn;

	private final BuddyEventStatus status = BuddyEventStatus.RECRUITING;

	private final String kakaoRoomCode;

	@Size(min = 0, max = 500, message = "코멘트는 최대 500자까지 입력 가능합니다.")
	private final String comment;

	@Min(value = 0, message = "레벨 조건의 최소는 0(누구나) 입니다.")
	@Max(value = 4, message = "레벨 조건의 최대는 4레벨 입니다.")
	private Integer freedivingLevel;

	private Set<DivingPool> divingPools;

	@Builder
	private CreateBuddyEventCommand(Long userId, LocalDateTime eventStartDate, LocalDateTime eventEndDate,
		Integer participantCount, Set<BuddyEventConcept> buddyEventConcepts, Boolean carShareYn, String kakaoRoomCode,
		String comment, Integer freedivingLevel, Set<DivingPool> divingPools) {
		this.userId = userId;
		this.eventStartDate = eventStartDate;

		if (eventEndDate.isAfter(eventStartDate) == false)
			throw new BuddyMeException(ServiceStatusCode.BAD_REQUEST, "일정 종료 시간은 시작 시간 이후여야 합니다.");
		this.eventEndDate = eventEndDate;

		this.participantCount = participantCount;
		this.buddyEventConcepts = buddyEventConcepts;
		this.carShareYn = (carShareYn == null) ? false : carShareYn; // Default False
		this.comment = comment;
		this.kakaoRoomCode = kakaoRoomCode;
		this.freedivingLevel = freedivingLevel;
		this.divingPools = divingPools;
		this.validateSelf();
	}
}
