package com.freediving.buddyservice.application.port.in.web.query.carouselsimple;

import java.time.LocalDateTime;

import com.freediving.common.SelfValidating;
import com.freediving.common.enumerate.DivingPool;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;

import jakarta.validation.constraints.FutureOrPresent;
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
public class GetBuddyEventCarouselSimpleCommand extends SelfValidating<GetBuddyEventCarouselSimpleCommand> {

	private final LocalDateTime eventStartDate;

	@FutureOrPresent(message = "일정 종료 시간은 현재 시간 이후여야 합니다.")
	private final LocalDateTime eventEndDate;
	private final DivingPool divingPool;
	private final Long excludedEventId;

	@Builder
	public GetBuddyEventCarouselSimpleCommand(LocalDateTime eventStartDate, LocalDateTime eventEndDate,
		DivingPool divingPool, Long excludedEventId) {

		if (eventStartDate.isAfter(LocalDateTime.now()) == false)
			eventStartDate = LocalDateTime.now();

		this.eventStartDate = eventStartDate;
		this.eventEndDate = eventEndDate;

		if (eventEndDate.isAfter(eventStartDate) == false)
			throw new BuddyMeException(ServiceStatusCode.BAD_REQUEST, "일정 종료 시간은 시작 시간 이후여야 합니다.");

		this.divingPool = divingPool;
		this.excludedEventId = excludedEventId;

		this.validateSelf();
	}
}
