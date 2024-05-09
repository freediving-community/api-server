package com.freediving.buddyservice.application.service.web;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.listing.BuddyEventConceptMappingProjectDto;
import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.listing.BuddyEventDivingPoolMappingProjectDto;
import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.listing.BuddyEventJoinMappingProjectDto;
import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.listing.GetBuddyEventListingQueryProjectionDto;
import com.freediving.buddyservice.application.port.in.web.query.listing.GetBuddyEventListingCommand;
import com.freediving.buddyservice.application.port.in.web.query.listing.GetBuddyEventListingUseCase;
import com.freediving.buddyservice.application.port.out.web.query.GetBuddyEventListingPort;
import com.freediving.buddyservice.domain.enumeration.ParticipationStatus;
import com.freediving.buddyservice.domain.query.QueryComponentListResponse;
import com.freediving.buddyservice.domain.query.component.BuddyEventlistingCardResponse;
import com.freediving.buddyservice.domain.query.component.common.ConceptInfoResponse;
import com.freediving.buddyservice.domain.query.component.common.DivingPoolInfoResponse;
import com.freediving.buddyservice.domain.query.component.common.ParticipantInfoResponse;
import com.freediving.buddyservice.domain.query.component.common.UserInfoResponse;
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
		List<GetBuddyEventListingQueryProjectionDto> buddyEventListing = getBuddyEventListingPort.getBuddyEventListing(
			1L, command.getEventStartDate(), command.getEventEndDate(),
			command.getBuddyEventConcepts(), command.getCarShareYn(), command.getFreedivingLevel(),
			command.getDivingPools(), command.getSortType(), command.getPageNumber(), command.getPageSize());

		final List<Long> ids = buddyEventListing.stream()
			.map(e -> e.getEventId())
			.collect(Collectors.toList());

		Map<Long, List<BuddyEventDivingPoolMappingProjectDto>> allDivingPoolMappingByEventId = getBuddyEventListingPort.getAllDivingPoolMapping(
			ids);

		Map<Long, List<BuddyEventConceptMappingProjectDto>> allConceptMappingByEventId = getBuddyEventListingPort.getAllConceptMapping(
			ids);

		Map<Long, List<BuddyEventJoinMappingProjectDto>> allJoinMappingByEventId = getBuddyEventListingPort.getAllJoinMapping(
			ids);

		QueryComponentListResponse response = new QueryComponentListResponse();

		for (GetBuddyEventListingQueryProjectionDto event : buddyEventListing) {
			List<BuddyEventConceptMappingProjectDto> conceptMappings = allConceptMappingByEventId.get(
				event.getEventId());
			List<BuddyEventDivingPoolMappingProjectDto> divingPoolMappings = allDivingPoolMappingByEventId.get(
				event.getEventId());
			List<BuddyEventJoinMappingProjectDto> joinMappings = allJoinMappingByEventId.get(event.getEventId());

			BuddyEventlistingCardResponse cardResponse = BuddyEventlistingCardResponse.builder()
				.user(joinMappings.stream().filter(e -> e.getStatus().equals(ParticipationStatus.OWNER))
					.map(e -> UserInfoResponse.builder().userId(e.getUserId()).build()).findFirst().get())
				.isLiked(event.isLiked())
				.likedCount(event.getLikedCount())
				.eventId(event.getEventId())
				.divingPools(Optional.ofNullable(divingPoolMappings)
					.orElse(Collections.emptyList()).stream()
					.map(e -> DivingPoolInfoResponse.builder()
						.divingPoolId(e.getDivingPoolId())
						.divingPoolName(e.getDivingPoolName())
						.build())
					.collect(
						Collectors.toSet()))
				.comment(event.getComment())
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
				.participantInfos(joinMappings.stream()
					.filter(e -> e.getStatus().equals(ParticipationStatus.PARTICIPATING))
					.map(e -> ParticipantInfoResponse.builder().userId(e.getUserId()).build())
					.collect(
						Collectors.toSet()))
				.build();

			response.getComponents().add(cardResponse);
		}

		return response;
	}
}
