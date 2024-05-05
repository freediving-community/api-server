package com.freediving.buddyservice.application.service.web;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.freediving.buddyservice.application.port.in.web.query.listing.GetBuddyEventListingCommand;
import com.freediving.buddyservice.application.port.in.web.query.listing.GetBuddyEventListingUseCase;
import com.freediving.buddyservice.application.port.out.web.query.GetBuddyEventListingPort;
import com.freediving.buddyservice.domain.query.QueryComponentListResponse;
import com.freediving.common.config.annotation.UseCase;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetBuddyEventListingService implements GetBuddyEventListingUseCase {

	private final GetBuddyEventListingPort getBuddyEventListingPort;

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public QueryComponentListResponse getBuddyEventListing(GetBuddyEventListingCommand command) {


		//dsl 쿼리 조회

		/* Query DSL 조회
		*
		* */


		// 멤버 서비스로 사용자 정보 요청.
		getBuddyEventListingPort.getBuddyEventListing(1L,command.getEventStartDate(),command.getEventEndDate(),command.getBuddyEventConcepts(),command.getCarShareYn(),command.getFreedivingLevel(),command.getDivingPools(),command.getSortType());


		return null;
	}
}
