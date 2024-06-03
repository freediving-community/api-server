package com.freediving.buddyservice.application.service.externalservice;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.freediving.buddyservice.adapter.out.persistence.concept.BuddyEventConceptJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.preference.UserBuddyEventConceptEntity;
import com.freediving.buddyservice.application.port.in.externalservice.query.GetBuddyEventConceptListUseCase;
import com.freediving.buddyservice.application.port.out.web.query.GetBuddyEventConceptListPort;
import com.freediving.buddyservice.domain.query.BuddyEventConceptListResponse;
import com.freediving.buddyservice.domain.query.UserConceptListResponse;
import com.freediving.common.config.annotation.UseCase;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional
public class GetBuddyEventConceptListService implements GetBuddyEventConceptListUseCase {

	private final GetBuddyEventConceptListPort getBuddyEventConceptListPort;

	@Override
	public BuddyEventConceptListResponse getEventConcepts() {

		List<BuddyEventConceptJpaEntity> eventConceptList = getBuddyEventConceptListPort.getEventConceptList();

		BuddyEventConceptListResponse result = BuddyEventConceptListResponse.builder().build();

		for (BuddyEventConceptJpaEntity entity : eventConceptList)
			result.add(
				BuddyEventConceptListResponse.EventConcept.builder().conceptId(entity.getConceptId()).conceptName(
					entity.getConceptName()).conceptDesc(entity.getConceptDesc()).build());

		return result;
	}

	@Override
	public UserConceptListResponse getEventConceptsForInternal(Long userId) throws BuddyMeException {

		List<UserBuddyEventConceptEntity> eventConceptList = getBuddyEventConceptListPort.getEventConceptListForInternal(
			userId);

		if (eventConceptList == null || eventConceptList.isEmpty())
			throw new BuddyMeException(ServiceStatusCode.NO_CONTENT);

		UserConceptListResponse result = UserConceptListResponse.builder().build();

		for (UserBuddyEventConceptEntity entity : eventConceptList)
			result.add(entity.getConceptId());

		return result;
	}
}
