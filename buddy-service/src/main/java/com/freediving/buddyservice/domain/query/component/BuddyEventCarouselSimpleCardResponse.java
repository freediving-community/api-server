package com.freediving.buddyservice.domain.query.component;

import java.time.LocalDateTime;
import java.util.Set;

import com.freediving.buddyservice.config.enumerate.GenderType;
import com.freediving.buddyservice.domain.enumeration.BuddyEventConcept;
import com.freediving.buddyservice.domain.enumeration.BuddyEventStatus;
import com.freediving.buddyservice.domain.query.common.ConceptInfoResponse;
import com.freediving.buddyservice.domain.query.common.DivingPoolInfoResponse;
import com.freediving.buddyservice.domain.query.common.UserInfoResponse;
import com.freediving.common.enumerate.DivingPool;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Schema(title = "버디 매칭 캐로셀 심플 조회 쿼리 컴포넌트 ( BuddyEventCarouselSimpleCardResponse )", description = "QueryComponent 객체이며, 캐로셀 심플 타입 조회의 응답 객체 입니다.")
public class BuddyEventCarouselSimpleCardResponse implements QueryComponent {

	@Schema(description = "사용자 정보", requiredMode = Schema.RequiredMode.REQUIRED)
	private UserInfoResponse userInfo;

	@Schema(description = "이벤트 ID", example = "12345", requiredMode = Schema.RequiredMode.REQUIRED)
	private Long eventId;

	@ArraySchema(arraySchema = @Schema(description = "다이빙 풀 정보"),
		schema = @Schema(implementation = DivingPool.class, requiredMode = Schema.RequiredMode.REQUIRED))
	private Set<DivingPoolInfoResponse> divingPools;

	@ArraySchema(arraySchema = @Schema(description = "컨셉 정보"),
		schema = @Schema(implementation = BuddyEventConcept.class, requiredMode = Schema.RequiredMode.REQUIRED))
	private Set<ConceptInfoResponse> concepts;

	@Schema(description = "이벤트 시작 날짜", example = "2023-05-16T09:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
	private LocalDateTime eventStartDate;

	@Schema(description = "이벤트 종료 날짜", example = "2023-05-16T12:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
	private LocalDateTime eventEndDate;

	@Schema(description = "프리다이빙 레벨제한(null:누구나)", example = "1~4", minimum = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	private Long freedivingLevel;

	@Schema(description = "이벤트 상태", implementation = BuddyEventStatus.class, requiredMode = Schema.RequiredMode.REQUIRED)
	private BuddyEventStatus status;

	@Schema(description = "참가자 수", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
	private Long participantCount;

	@Schema(description = "현재 참가자 수", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
	private Long currentParticipantCount;

	@Schema(description = "성별 타입", example = "ALL", implementation = GenderType.class, requiredMode = Schema.RequiredMode.REQUIRED)
	private GenderType genderType;

}
