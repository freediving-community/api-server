package com.freediving.buddyservice.application.port.in.externalservice.query;

import com.freediving.buddyservice.domain.query.BuddyEventConceptListResponse;
import com.freediving.buddyservice.domain.query.UserConceptListResponse;
import com.freediving.common.config.annotation.UseCase;

/**
 * 버디 이벤트 컨셉 조회
 *
 * @author 준희조
 * @version 1.0.0
 * 작성일 2024-03-21
 **/

@UseCase
public interface GetBuddyEventConceptListUseCase {

	/**
	 * 버디 이벤트 컨셉 조회
	 *
	 * @return 버디 이벤트 컨셉 리스트
	 */
	BuddyEventConceptListResponse getEventConcepts();

	UserConceptListResponse getEventConceptsForInternal(Long userId);
}
