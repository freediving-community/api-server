package com.freediving.buddyservice.application.port.in.web.query.home;

import java.time.LocalDateTime;

import com.freediving.common.SelfValidating;

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
public class GetHomeWeeklyBuddyEventCommand extends SelfValidating<GetHomeWeeklyBuddyEventCommand> {

	private final LocalDateTime eventStartDate;

	@Min(1)
	private Integer pageNumber;

	@Min(1)
	private Integer pageSize;

	@Builder
	public GetHomeWeeklyBuddyEventCommand(LocalDateTime eventStartDate, int pageNumber, int pageSize) {

		if (eventStartDate.isAfter(LocalDateTime.now()) == false)
			eventStartDate = LocalDateTime.now();

		this.eventStartDate = eventStartDate;

		this.pageNumber = pageNumber;
		this.pageSize = pageSize;

		this.validateSelf();
	}
}
