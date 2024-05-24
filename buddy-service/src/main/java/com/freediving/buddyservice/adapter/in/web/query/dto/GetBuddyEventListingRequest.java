package com.freediving.buddyservice.adapter.in.web.query.dto;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.freediving.buddyservice.config.enumerate.GenderType;
import com.freediving.buddyservice.config.enumerate.SortType;
import com.freediving.buddyservice.domain.enumeration.BuddyEventConcept;
import com.freediving.common.enumerate.DivingPool;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(title = "버디 이벤트 조회 요청 (리스팅)", name = "GetBuddyEventListingRequest", description = "GET /v1/event/listing 버디 이벤트 조회 요청 Schema", hidden = true)
public class GetBuddyEventListingRequest {

	@Parameter(description = "일정 시작 시간", example = "2024-05-23T11:59:59",
		schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED))
	@NotNull(message = "일정 시작 시간은 필수입니다.")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime eventStartDate;

	@Parameter(description = "일정 종료 시간", example = "2024-05-23T11:59:59",
		schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
	)
	@NotNull(message = "일정 종료 시간은 필수입니다.")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime eventEndDate;

	@Parameter(description = "버디 이벤트 컨셉 ( 상관 없음 null )",
		array = @ArraySchema(
			schema = @Schema(implementation = BuddyEventConcept.class, requiredMode = Schema.RequiredMode.NOT_REQUIRED)))
	private Set<BuddyEventConcept> buddyEventConcepts;

	@Parameter(description = "카셰어링 여부 ( 상관 없음 null )",
		schema = @Schema(example = "true", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	)
	private Boolean carShareYn;

	@Parameter(description = "프리다이빙 레벨 제한 ( 상관 없음 null )",
		schema = @Schema(example = "1", minimum = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED))
	private Integer freedivingLevel;

	@Parameter(description = "다이빙 풀",
		array = @ArraySchema(
			schema = @Schema(implementation = DivingPool.class, requiredMode = Schema.RequiredMode.NOT_REQUIRED)))
	private Set<DivingPool> divingPools;

	@Parameter(description = "정렬 타입 ( null인 경우 최신순)",
		schema = @Schema(example = "NEWEST", implementation = SortType.class, requiredMode = Schema.RequiredMode.NOT_REQUIRED))
	private SortType sortType = SortType.NEWEST;

	@Parameter(description = "성별 타입 ( null인 경우 ALL )",
		schema = @Schema(example = "ALL", implementation = GenderType.class, requiredMode = Schema.RequiredMode.NOT_REQUIRED))
	private GenderType genderType = GenderType.ALL;

	@Parameter(description = "페이지 번호",
		schema = @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED))
	@NotNull(message = "pageNumber is null")
	@Min(1)
	private Integer pageNumber;

	@Parameter(description = "페이지당 사이즈",
		schema = @Schema(example = "10", requiredMode = Schema.RequiredMode.REQUIRED))
	@NotNull(message = "pageSize is null")
	@Min(1)
	private Integer pageSize;
}
