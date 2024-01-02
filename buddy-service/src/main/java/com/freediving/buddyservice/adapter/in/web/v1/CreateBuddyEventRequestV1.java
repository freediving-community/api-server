package com.freediving.buddyservice.adapter.in.web.v1;

import java.time.LocalDateTime;
import java.util.List;

import com.freediving.buddyservice.common.enumeration.EventConcept;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * CreateBuddyEvent API 요청의 Body Json 직렬화 할 객체.
 * validation @Valid 유효성 체크를 진행하나, Adapter Layer 유효성만 체크한다.*
 * @author 준희조
 * @version 1.0.0
 * 작성일 2023-12-28
 **/
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CreateBuddyEventRequestV1 {

	@NotNull(message = "일정 시작 시간은 필수입니다.")
	private LocalDateTime eventStartDate;

	@NotNull(message = "일정 종료 시간은 필수입니다.")
	private LocalDateTime eventEndDate;

	@NotNull
	private Integer participantCount;

	//TODO 버디 일정 이벤트 컨셉 옵셔널 정책 확정 후 수정 필요.
	private List<EventConcept> eventConcepts;

	@NotNull
	private Boolean carShareYn;

	private String comment;

}
