package com.freediving.buddyservice.application.service.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.freediving.buddyservice.adapter.out.externalservice.member.userinfo.dto.UserInfo;
import com.freediving.buddyservice.adapter.out.persistence.event.concept.querydsl.BuddyEventConceptMappingProjectDto;
import com.freediving.buddyservice.adapter.out.persistence.event.divingpool.querydsl.BuddyEventDivingPoolMappingProjectDto;
import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.caroselsimple.GetBuddyEventCarouselSimpleQueryProjectionDto;
import com.freediving.buddyservice.application.port.in.web.query.carouselsimple.GetBuddyEventCarouselSimpleCommand;
import com.freediving.buddyservice.application.port.in.web.query.carouselsimple.GetBuddyEventCarouselSimpleUseCase;
import com.freediving.buddyservice.application.port.out.Internalservice.query.RequestMemberPort;
import com.freediving.buddyservice.application.port.out.web.query.BuddyEventConceptMappingPort;
import com.freediving.buddyservice.application.port.out.web.query.BuddyEventDivingPoolMappingPort;
import com.freediving.buddyservice.application.port.out.web.query.GetBuddyEventCarouselSimplePort;
import com.freediving.buddyservice.domain.query.QueryComponentListWithoutPageResponse;
import com.freediving.buddyservice.domain.query.common.ConceptInfoResponse;
import com.freediving.buddyservice.domain.query.common.DivingPoolInfoResponse;
import com.freediving.buddyservice.domain.query.component.BuddyEventCarouselSimpleCardResponse;
import com.freediving.common.config.annotation.UseCase;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetBuddyEventCarouselSimpleService implements GetBuddyEventCarouselSimpleUseCase {

	private final GetBuddyEventCarouselSimplePort getBuddyEventCarouselSimplePort;
	private final RequestMemberPort requestMemberPort;
	private final BuddyEventDivingPoolMappingPort buddyEventDivingPoolMappingPort;
	private final BuddyEventConceptMappingPort buddyEventConceptMappingPort;

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	@Override
	public QueryComponentListWithoutPageResponse getBuddyEventCarouselSimple(
		GetBuddyEventCarouselSimpleCommand command) {
		List<GetBuddyEventCarouselSimpleQueryProjectionDto> buddyEvents = getBuddyEventCarouselSimplePort.getBuddyEventCarouselSimple(
			command.getEventStartDate(), command.getEventEndDate(), command.getDivingPool(),
			command.getExcludedEventId());

		if (buddyEvents == null || buddyEvents.isEmpty())
			throw new BuddyMeException(ServiceStatusCode.NO_CONTENT);

		final List<Long> ids = buddyEvents.stream()
			.map(e -> e.getEventId())
			.collect(Collectors.toList());

		Map<Long, List<BuddyEventDivingPoolMappingProjectDto>> allDivingPoolMappingByEventId = buddyEventDivingPoolMappingPort.getAllDivingPoolMapping(
			ids);

		Map<Long, List<BuddyEventConceptMappingProjectDto>> allConceptMappingByEventId = buddyEventConceptMappingPort.getAllConceptMapping(
			ids);

		Set<Long> userIds = new HashSet<>();
		// 사용자 정보 요청할 사용자 ID 수집
		for (GetBuddyEventCarouselSimpleQueryProjectionDto event : buddyEvents) {
			// 사용자 정보 요청할 사용자 ID 수집
			userIds.add(event.getUserId());
		}

		HashMap<Long, UserInfo> userHashMap = requestMemberPort.getMemberStatus(userIds.stream().toList());

		QueryComponentListWithoutPageResponse response = QueryComponentListWithoutPageResponse.builder()
			.components(new ArrayList<>())
			.build();

		for (GetBuddyEventCarouselSimpleQueryProjectionDto event : buddyEvents) {

			//작성자 정보를 못가져오면 제거.
			UserInfo buddyOwner = userHashMap.get(event.getUserId());
			if (buddyOwner == null)
				continue;

			List<BuddyEventConceptMappingProjectDto> conceptMappings = allConceptMappingByEventId.get(
				event.getEventId());
			List<BuddyEventDivingPoolMappingProjectDto> divingPoolMappings = allDivingPoolMappingByEventId.get(
				event.getEventId());

			BuddyEventCarouselSimpleCardResponse cardResponse = BuddyEventCarouselSimpleCardResponse.builder()
				.userInfo(buddyOwner.toResponse())
				.eventId(event.getEventId())
				.divingPools(Optional.ofNullable(divingPoolMappings)
					.orElse(Collections.emptyList()).stream()
					.map(e -> DivingPoolInfoResponse.builder()
						.divingPoolId(e.getDivingPoolId())
						.divingPoolName(e.getDivingPoolName())
						.build())
					.collect(
						Collectors.toSet()))
				.concepts(Optional.ofNullable(conceptMappings)
					.orElse(Collections.emptyList()).stream()
					.map(e -> ConceptInfoResponse.builder()
						.conceptId(e.getConceptId())
						.conceptName(e.getConceptName())
						.build())
					.collect(
						Collectors.toSet()))
				.eventStartDate(event.getEventStartDate())
				.eventEndDate(event.getEventEndDate())
				.freedivingLevel(event.getFreedivingLevel())
				.status(event.getStatus())
				.participantCount(event.getParticipantCount())
				.currentParticipantCount(event.getCurrentParticipantCount() - 1)
				.genderType(event.getGenderType())
				.build();

			response.getComponents().add(cardResponse);
		}

		return response;
	}
}
