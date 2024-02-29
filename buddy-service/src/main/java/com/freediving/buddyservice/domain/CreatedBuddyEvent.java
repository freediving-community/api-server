package com.freediving.buddyservice.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.freediving.buddyservice.common.enumeration.EventConcept;
import com.freediving.buddyservice.common.enumeration.EventStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter

@Schema(title = "버디 이벤트 생성 결과 ( CreatedBuddyEvent )", description = "POST /v1/event 버디 이벤트 생성 결과", hidden = true)
public class CreatedBuddyEvent {

	@Schema(description = "이벤트 ID", example = "12345")
	private final Long eventId;

	@Schema(description = "사용자 ID", example = "67890")
	private final Long userId;

	@Schema(description = "이벤트 시작 시간", example = "2024-01-17 15:30:00")
	private final LocalDateTime eventStartDate;

	@Schema(description = "이벤트 종료 시간", example = "2024-01-17 17:30:00")
	private final LocalDateTime eventEndDate;

	@Schema(description = "참여자 수", example = "5")
	private final Integer participantCount;

	@Schema(description = "버디 이벤트 컨셉", implementation = EventConcept.class)
	private final List<EventConcept> eventConcepts;

	@Schema(description = "카셰어링 여부", example = "true")
	private final Boolean carShareYn;

	@Schema(description = "이벤트 상태", implementation = EventStatus.class)
	private final EventStatus status;

	@Schema(description = "카카오 채팅방 코드", example = "gQWkq2Uf", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	private final String kakaoRoomCode;

	@Schema(description = "추가 코멘트", example = "이번 모임은 캐주얼하게 진행합니다.")
	private final String comment;

	@Schema(description = "생성 일자", example = "2024-01-17 12:00:00")
	private final LocalDateTime createdDate;

	@Schema(description = "업데이트 일자", example = "2024-01-18 12:00:00")
	private final LocalDateTime updatedDate;

}
