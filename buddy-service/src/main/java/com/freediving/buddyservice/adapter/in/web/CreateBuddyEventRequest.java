package com.freediving.buddyservice.adapter.in.web;

import java.time.LocalDateTime;
import java.util.List;

import com.freediving.buddyservice.common.enumeration.EventConcept;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(title = "버디 이벤트 생성 요청 ( CreateBuddyEventRequest )", name = "name", description = "POST /v1/event 버디 이벤트 생성에 요청 Schema")
public class CreateBuddyEventRequest {

	@Schema(description = "일정 시작 시간", example = "2024-01-17 15:30:00", required = true)
	@NotNull(message = "일정 시작 시간은 필수입니다.")
	private LocalDateTime eventStartDate;

	@Schema(description = "일정 종료 시간", example = "2024-01-17 17:30:00", required = true)
	@NotNull(message = "일정 종료 시간은 필수입니다.")
	private LocalDateTime eventEndDate;

	@Schema(description = "참여자 수", example = "5", required = true)
	@NotNull
	private Integer participantCount;

	@ArraySchema(arraySchema = @Schema(description = "버디 이벤트 컨셉"),
		schema = @Schema(implementation = EventConcept.class))
	private List<EventConcept> eventConcepts;

	@Schema(description = "카셰어링 여부", example = "true", required = true)
	@NotNull
	private Boolean carShareYn;

	@Schema(description = "추가 코멘트", example = "이번 모임은 캐주얼하게 진행합니다.")
	private String comment;

}
