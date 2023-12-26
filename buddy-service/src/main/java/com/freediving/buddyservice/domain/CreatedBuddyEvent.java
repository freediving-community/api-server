package com.freediving.buddyservice.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.freediving.buddyservice.common.enumeration.EventConcept;
import com.freediving.buddyservice.common.enumeration.EventStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class CreatedBuddyEvent {

	private final Long eventId;
	private final Long userId;
	private final LocalDateTime eventStartDate;
	private final LocalDateTime eventEndDate;
	private final Integer participantCount;

	//TODO 버디 일정 이벤트 컨셉 옵셔널 정책 확정 후 수정 필요.
	private final List<EventConcept> eventConcepts;

	// Default Value is False
	private final Boolean carShareYn;
	private final EventStatus status;
	private final String comment;
	private final LocalDateTime createdDate;
	private final LocalDateTime updatedDate;

}
