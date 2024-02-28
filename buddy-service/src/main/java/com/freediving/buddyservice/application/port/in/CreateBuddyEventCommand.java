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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
@Getter
public class CreateBuddyEventCommand extends SelfValidating<CreateBuddyEventCommand> {

	@NotNull
	private final Long userId;

	@NotNull(message = "일정 시작 시간은 필수입니다.")
	@FutureOrPresent(message = "일정 시작 시간은 현재 시간 이후여야 합니다.")
	private final LocalDateTime eventStartDate;

	@NotNull(message = "일정 종료 시간은 필수입니다.")
	@FutureOrPresent(message = "일정 종료 시간은 현재 시간 이후여야 합니다.")
	private final LocalDateTime eventEndDate;

	@Positive(message = "모집 인원은 0 또는 양수여야 합니다.")
	@Max(value = 5L, message = "최대 모집 인원은 999명까지 가능합니다.")
	@NotNull(message = "모집 인원은 필수입니다.")
	private final Integer participantCount;

	//TODO 버디 일정 이벤트 컨셉 옵셔널 정책 확정 후 수정 필요.
	private final List<EventConcept> eventConcepts;

	private final Boolean carShareYn;

	private final EventStatus status = EventStatus.RECRUITING;

	@Size(min = 0, max = 500, message = "코멘트는 최대 500자까지 입력 가능합니다.")
	private final String comment;

	@Builder
	private CreateBuddyEventCommand(Long userId, LocalDateTime eventStartDate, LocalDateTime eventEndDate,
		Integer participantCount, List<EventConcept> eventConcepts, Boolean carShareYn, String comment) {
		this.userId = userId;
		this.eventStartDate = eventStartDate;

		if (eventEndDate.isAfter(eventStartDate) == false)
			throw new BuddyMeException(ServiceStatusCode.BAD_REQUEST, "일정 종료 시간은 시작 시간 이후여야 합니다.");
		this.eventEndDate = eventEndDate;

		this.participantCount = participantCount;
		this.eventConcepts = eventConcepts;
		this.carShareYn = (carShareYn == null) ? false : carShareYn; // Default False
		this.comment = comment;

		this.validateSelf();
	}
}
