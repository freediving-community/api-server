package com.freediving.buddyservice.domain.command;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.freediving.buddyservice.config.enumerate.GenderType;
import com.freediving.buddyservice.domain.enumeration.BuddyEventConcept;
import com.freediving.buddyservice.domain.enumeration.BuddyEventStatus;
import com.freediving.common.enumerate.DivingPool;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Schema(title = "버디 이벤트 생성 결과 ( CreatedBuddyEventResponse )", description = "POST /v1/event 버디 이벤트 생성 결과")
public class CreatedBuddyEventResponse {

	@Schema(description = "이벤트 ID", example = "12345", requiredMode = Schema.RequiredMode.REQUIRED)
	private final Long eventId;

	@Schema(description = "사용자 ID", example = "67890", requiredMode = Schema.RequiredMode.REQUIRED)
	private final Long userId;

	@Schema(description = "이벤트 시작 시간", type = "string", example = "2024-05-23T11:59:59", requiredMode = Schema.RequiredMode.REQUIRED)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private final LocalDateTime eventStartDate;

	@Schema(description = "이벤트 종료 시간", type = "string", example = "2024-05-23T11:59:59", requiredMode = Schema.RequiredMode.REQUIRED)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private final LocalDateTime eventEndDate;

	@Schema(description = "참여자 수", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
	private final Integer participantCount;

	@ArraySchema(arraySchema = @Schema(description = "버디 이벤트 컨셉"),
		schema = @Schema(implementation = BuddyEventConcept.class, requiredMode = Schema.RequiredMode.REQUIRED))
	private final Set<BuddyEventConcept> buddyEventConcepts;

	@Schema(description = "카셰어링 여부", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
	private final Boolean carShareYn;

	@Schema(description = "이벤트 상태", implementation = BuddyEventStatus.class, requiredMode = Schema.RequiredMode.REQUIRED)
	private final BuddyEventStatus status;

	@Schema(description = "카카오 채팅방 코드", example = "gQWkq2Uf", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	private final String kakaoRoomCode;

	@Schema(description = "추가 코멘트", example = "이번 모임은 캐주얼하게 진행합니다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	private final String comment;

	@Schema(description = "생성 일자", type = "string", example = "2024-05-23T11:59:59", requiredMode = Schema.RequiredMode.REQUIRED)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private final LocalDateTime createdDate;

	@Schema(description = "업데이트 일자", type = "string", example = "2024-05-23T11:59:59", requiredMode = Schema.RequiredMode.REQUIRED)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private final LocalDateTime updatedDate;

	@Schema(description = "성별 여부", implementation = GenderType.class, requiredMode = Schema.RequiredMode.REQUIRED)
	private GenderType genderType;

	@Schema(description = "프리다이빙 레벨제한(null:누구나)", example = "1~4", minimum = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	private Integer freedivingLevel;

	@ArraySchema(arraySchema = @Schema(description = "다이빙 풀"),
		schema = @Schema(implementation = DivingPool.class, requiredMode = Schema.RequiredMode.REQUIRED))
	private Set<DivingPool> divingPools;

	@Schema(description = "이미지 CDN URL", example = "https://cdn.aws.com:123/asdf/images/123qwe456asd")
	private String imageUrl;
}
