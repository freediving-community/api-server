package com.freediving.buddyservice.adapter.in.web.query.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.freediving.buddyservice.config.enumerate.SortType;
import com.freediving.buddyservice.domain.enumeration.BuddyEventConcept;
import com.freediving.common.enumerate.DivingPool;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
* 버디 이벤트를 리스팅 카드 형태로 조회 하기 위한 요청 객체
 * 버디미 서비스에서 버디 매칭 메뉴에서 사용된다.
 * 검색 조건으로는 아래와 같다.
 *   - 정렬 타입 : 인기순, 마감 임박순, 최신순
 *   - 날짜 1일 선택, 다이빙 시간 ( from-to or 무관 ), 다이빙 풀 최대 2개 ( OR 조건 ), 컨셉 여러개 ( IN 조건 모두 포함 )
 *   -  버디 레벨 ( 누구나, 레벨 숫자 ), 차량 여부 ( 상관 없음, 차량 없음, 동승 가능 )
*
* @author pus__
* @version 1.0.0
* 작성일 2024-05-05
**/

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(title = "버디 이벤트 조회 요청 (리스팅)", name = "GetBuddyEventListingRequest", description = "GET /v1/event/listing 버디 이벤트 조회 요청 Schema")
public class GetBuddyEventListingRequest {

	@Schema(description = "일정 시작 시간", type = "string", example = "2024-05-17 15:30:00", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull(message = "일정 시작 시간은 필수입니다.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime eventStartDate;

	@Schema(description = "일정 종료 시간", type = "string", example = "2024-05-17 17:30:00", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull(message = "일정 종료 시간은 필수입니다.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime eventEndDate;

	@ArraySchema(arraySchema = @Schema(description = "버디 이벤트 컨셉"),
		schema = @Schema(implementation = BuddyEventConcept.class, requiredMode = Schema.RequiredMode.NOT_REQUIRED))
	private Set<BuddyEventConcept> buddyEventConcepts;

	@Schema(description = "카셰어링 여부 ( 상관 없음 null )", example = "true", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	private Boolean carShareYn;

	@Schema(description = "프리다이빙 레벨 제한", example = "0~3", minimum = "0", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	private Integer freedivingLevel;

	@ArraySchema(arraySchema = @Schema(description = "다이빙 풀"),
		schema = @Schema(implementation = DivingPool.class, requiredMode = Schema.RequiredMode.NOT_REQUIRED))
	private Set<DivingPool> divingPools;

	@Schema(description = "정렬 타입", example = "POPULARITY" , implementation = SortType.class, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	private SortType sortType;
}
