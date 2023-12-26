package com.freediving.buddyservice.application.port.in;

import java.time.LocalDateTime;
import java.util.List;

import com.freediving.buddyservice.common.enumeration.EventConcept;
import com.freediving.buddyservice.common.enumeration.EventStatus;
import com.freediving.buddyservice.common.enumeration.SelfValidating;

import jakarta.validation.ConstraintDeclarationException;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
@Getter
public class CreateBuddyEventCommand extends SelfValidating<CreateBuddyEventCommand> {

	private final Long userId;

	@FutureOrPresent
	private final LocalDateTime eventStartDate;
	@FutureOrPresent
	private final LocalDateTime eventEndDate;
	@Positive
	private final Integer participantCount;
	//TODO 버디 일정 이벤트 컨셉 옵셔널 정책 확정 후 수정 필요.
	private final List<EventConcept> eventConcepts;

	private final Boolean carShareYn; // Default False

	private final EventStatus status = EventStatus.RECRUITING;

	private final String comment;

	@Builder
	private CreateBuddyEventCommand(Long userId, LocalDateTime eventStartDate, LocalDateTime eventEndDate,
		Integer participantCount, List<EventConcept> eventConcepts, Boolean carShareYn, String comment) {
		this.userId = userId;
		this.eventStartDate = eventStartDate;
		this.eventEndDate = eventEndDate;
		this.participantCount = participantCount;
		this.eventConcepts = eventConcepts;
		this.carShareYn = carShareYn;
		if (comment != null && comment.length() > 1000)
			throw new ConstraintDeclarationException();
		this.comment = comment;
	}
}
