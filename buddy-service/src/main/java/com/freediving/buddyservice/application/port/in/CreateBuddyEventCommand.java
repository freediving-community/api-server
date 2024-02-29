package com.freediving.buddyservice.application.port.in;

import java.time.LocalDateTime;
import java.util.List;

import com.freediving.buddyservice.common.enumeration.EventConcept;
import com.freediving.buddyservice.common.enumeration.EventStatus;
import com.freediving.common.SelfValidating;
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
	
	private final List<EventConcept> eventConcepts;

	private final Boolean carShareYn;

	private final EventStatus status = EventStatus.RECRUITING;

	private final String kakaoRoomCode;

	@Size(min = 0, max = 500, message = "코멘트는 최대 500자까지 입력 가능합니다.")
	private final String comment;

	@Builder
	private CreateBuddyEventCommand(Long userId, LocalDateTime eventStartDate, LocalDateTime eventEndDate,
		Integer participantCount, List<EventConcept> eventConcepts, Boolean carShareYn, String kakaoRoomCode,
		String comment) {
		this.userId = userId;
		this.eventStartDate = eventStartDate;

		if (eventEndDate.isAfter(eventStartDate) == false)
			throw new BuddyMeException(ServiceStatusCode.BAD_REQUEST, "일정 종료 시간은 시작 시간 이후여야 합니다.");
		this.eventEndDate = eventEndDate;

		this.participantCount = participantCount;
		this.eventConcepts = eventConcepts;
		this.carShareYn = (carShareYn == null) ? false : carShareYn; // Default False
		this.comment = comment;
		this.kakaoRoomCode = kakaoRoomCode;
		this.validateSelf();
	}
}
