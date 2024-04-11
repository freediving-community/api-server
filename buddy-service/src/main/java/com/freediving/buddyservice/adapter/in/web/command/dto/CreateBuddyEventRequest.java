package com.freediving.buddyservice.adapter.in.web.command.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.freediving.buddyservice.common.enumeration.BuddyEventConcept;
import com.freediving.common.enumerate.DivingPool;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * CreateBuddyEvent API 요청의 Body Json 직렬화 할 객체.
 * validation @Valid 유효성 체크를 진행하나, Adapter Layer 유효성만 체크한다.*
 * 파라미터의 필수 여부: 요청에서 필수적으로 포함되어야 하는 파라미터가 실제로 포함되었는지 검증합니다.
 * 형식 검증: 파라미터의 데이터 형식(예: 문자열, 숫자, 날짜 등)이 올바른지 검증합니다.
 * 범위 검증: 숫자 파라미터가 특정 범위 내에 있는지 검증합니다. 예를 들어, 인원수가 최소 1명 이상이어야 한다는 조건을 검증할 수 있습니다.
 * @author 준희조
 * @version 1.0.0
 * 작성일 2023-12-28
 **/

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(title = "버디 이벤트 생성 요청 ( CreateBuddyEventRequest )", name = "CreateBuddyEventRequest", description = "POST /v1/event 버디 이벤트 생성에 요청 Schema")
public class CreateBuddyEventRequest {

	@Schema(description = "일정 시작 시간", type = "string", example = "2024-01-17 15:30:00", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull(message = "일정 시작 시간은 필수입니다.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime eventStartDate;

	@Schema(description = "일정 종료 시간", type = "string", example = "2024-01-17 17:30:00", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull(message = "일정 종료 시간은 필수입니다.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime eventEndDate;

	@Schema(description = "참여자 수", example = "5", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "1", maximum = "5")
	@Positive(message = "모집 인원은 0 또는 양수여야 합니다.")
	@NotNull(message = "모집 인원은 필수입니다.")
	private Integer participantCount;

	@ArraySchema(arraySchema = @Schema(description = "버디 이벤트 컨셉"),
		schema = @Schema(implementation = BuddyEventConcept.class, requiredMode = Schema.RequiredMode.NOT_REQUIRED))
	private Set<BuddyEventConcept> buddyEventConcepts;

	@Schema(description = "카셰어링 여부", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull
	private Boolean carShareYn;

	@Schema(description = "카카오 채팅방 코드", example = "gQWkq2Uf", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	private String kakaoRoomCode;

	@Schema(description = "추가 코멘트", example = "이번 모임은 캐주얼하게 진행합니다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	private String comment;

	@Schema( description = "프리다이빙 레벨 제한", example = "0~3" ,minimum = "0",  requiredMode = Schema.RequiredMode.REQUIRED)
	private Integer freedivingLevel;

	@ArraySchema(arraySchema = @Schema(description = "다이빙 풀"),
		schema = @Schema(implementation = DivingPool.class, requiredMode = Schema.RequiredMode.REQUIRED))
	private Set<DivingPool> divingPools;

}
