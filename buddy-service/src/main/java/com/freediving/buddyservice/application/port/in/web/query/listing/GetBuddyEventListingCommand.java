package com.freediving.buddyservice.application.port.in.web.query.listing;

import java.time.LocalDateTime;
import java.util.Set;

import com.freediving.buddyservice.config.enumerate.SortType;
import com.freediving.buddyservice.domain.enumeration.BuddyEventConcept;
import com.freediving.common.SelfValidating;
import com.freediving.common.enumerate.DivingPool;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 버디 이벤트 리스팅 카드 타입의 조회를 요청하는 명령 객체이다.
 * 주로 서비스 관련 벨리데이션 체크를 진행하고 Service Layer로 명령을 한다.
 *
 * @author pus__
 * @version 1.0.0
 * 작성일 2024-05-05
 **/
@EqualsAndHashCode(callSuper = false)
@Getter
public class GetBuddyEventListingCommand extends SelfValidating<GetBuddyEventListingCommand> {

	@FutureOrPresent(message = "일정 시작 시간은 현재 시간 이후여야 합니다.")
	private final LocalDateTime eventStartDate;

	@FutureOrPresent(message = "일정 종료 시간은 현재 시간 이후여야 합니다.")
	private final LocalDateTime eventEndDate;

	private final Set<BuddyEventConcept> buddyEventConcepts;
	private final Boolean carShareYn;

	@Min(value = 0, message = "레벨 조건의 최소는 0(누구나) 입니다.")
	@Max(value = 4, message = "레벨 조건의 최대는 4레벨 입니다.")
	private Integer freedivingLevel;

	private Set<DivingPool> divingPools;

	@Schema(description = "정렬 타입", example = "POPULARITY", implementation = SortType.class, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	private SortType sortType;

	@Schema(description = "페이지 번호", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
	@Min(1)
	private Integer pageNumber;

	@Schema(description = "페이지당 사이즈", example = "POPULARITY", requiredMode = Schema.RequiredMode.REQUIRED)
	@Min(1)
	private Integer pageSize;

	@Builder
	public GetBuddyEventListingCommand(LocalDateTime eventStartDate, LocalDateTime eventEndDate,
		Set<BuddyEventConcept> buddyEventConcepts, Boolean carShareYn, Integer freedivingLevel,
		Set<DivingPool> divingPools,
		SortType sortType, int pageNumber, int pageSize) {
		this.eventStartDate = eventStartDate;
		this.eventEndDate = eventEndDate;
		if (eventEndDate.isAfter(eventStartDate) == false)
			throw new BuddyMeException(ServiceStatusCode.BAD_REQUEST, "일정 종료 시간은 시작 시간 이후여야 합니다.");
		this.buddyEventConcepts = buddyEventConcepts;
		this.carShareYn = carShareYn;
		this.freedivingLevel = freedivingLevel;
		this.divingPools = divingPools;
		this.sortType = sortType;
		this.pageNumber = pageNumber;

		this.validateSelf();
	}
}
