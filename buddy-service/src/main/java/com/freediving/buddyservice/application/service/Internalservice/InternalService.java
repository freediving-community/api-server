package com.freediving.buddyservice.application.service.Internalservice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.freediving.buddyservice.adapter.out.persistence.concept.BuddyEventConceptJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.listing.BuddyEventJoinMappingProjectDto;
import com.freediving.buddyservice.adapter.out.persistence.preference.UserBuddyEventConceptEntity;
import com.freediving.buddyservice.application.port.in.internalservice.query.InternalUseCase;
import com.freediving.buddyservice.application.port.out.web.query.BuddyEventJoinPort;
import com.freediving.buddyservice.application.port.out.web.query.GetBuddyEventConceptListPort;
import com.freediving.buddyservice.domain.enumeration.ParticipationStatus;
import com.freediving.buddyservice.domain.query.BuddyEventConceptListResponse;
import com.freediving.buddyservice.domain.query.UserConceptListResponse;
import com.freediving.common.config.annotation.UseCase;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional
public class InternalService implements InternalUseCase {

	private final GetBuddyEventConceptListPort getBuddyEventConceptListPort;
	private final BuddyEventJoinPort buddyEventJoinPort;

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

	@Override
	public List<Long> getParticipantsOfEvent(Long eventId) {

		Map<Long, List<BuddyEventJoinMappingProjectDto>> participantsOfEvent = buddyEventJoinPort.getParticipantsOfEvent(
			eventId);

		if (participantsOfEvent == null || participantsOfEvent.isEmpty())
			throw new BuddyMeException(ServiceStatusCode.NO_CONTENT);

		List<Long> participants = participantsOfEvent.get(eventId)
			.stream()
			.filter(e -> e.getStatus().equals(ParticipationStatus.PARTICIPATING))
			.map(e -> e.getUserId())
			.collect(
				Collectors.toList());
		return participants;
	}
}
