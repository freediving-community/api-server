package com.freediving.buddyservice.adapter.in.web.v1;

import java.time.LocalDateTime;
import java.util.List;

import com.freediving.buddyservice.common.enumeration.EventConcept;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBuddyEventRequestV1 {

	@NotNull
	private LocalDateTime eventStartDate;
	@NotNull
	private LocalDateTime eventEndDate;
	@NotNull
	private Integer participantCount;
	//TODO 버디 일정 이벤트 컨셉 옵셔널 정책 확정 후 수정 필요.
	private List<EventConcept> eventConcepts;

	@NotNull
	// Default Value is False
	private Boolean carShareYn;

	private String comment;

}
