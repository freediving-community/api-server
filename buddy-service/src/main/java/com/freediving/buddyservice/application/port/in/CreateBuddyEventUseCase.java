package com.freediving.buddyservice.application.port.in;

import com.freediving.buddyservice.domain.CreatedBuddyEvent;

/**
 * 버디 일정 이벤트 생성 Use Case
 *
 * @author 준희조
 * @version 1.0.0
 * 작성일 2023-12-27
 **/

public interface CreateBuddyEventUseCase {

	/**
	 * 버디 일정 이벤트 생성 Use Case
	 *
	 * @param  command  버디 일정 이벤트 생성의 Command 정보를 담은 객체
	 * @return 생성 완료된 버디 일정 이벤트 도메인 객체
	 */
	CreatedBuddyEvent createBuddyEvent(CreateBuddyEventCommand command);
}
