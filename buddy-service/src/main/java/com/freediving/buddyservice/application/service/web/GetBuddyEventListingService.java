package com.freediving.buddyservice.application.service.web;

import java.time.LocalDateTime;
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
import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.listing.BuddyEventJoinMappingProjectDto;
import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.listing.GetBuddyEventListingQueryProjectionDto;
import com.freediving.buddyservice.application.port.in.web.query.listing.GetBuddyEventListingCommand;
import com.freediving.buddyservice.application.port.in.web.query.listing.GetBuddyEventListingUseCase;
import com.freediving.buddyservice.application.port.out.Internalservice.query.RequestMemberPort;
import com.freediving.buddyservice.application.port.out.web.query.BuddyEventConceptMappingPort;
import com.freediving.buddyservice.application.port.out.web.query.BuddyEventDivingPoolMappingPort;
import com.freediving.buddyservice.application.port.out.web.query.BuddyEventJoinPort;
import com.freediving.buddyservice.application.port.out.web.query.GetBuddyEventListingPort;
import com.freediving.buddyservice.domain.enumeration.ParticipationStatus;
import com.freediving.buddyservice.domain.query.QueryComponentListResponse;
import com.freediving.buddyservice.domain.query.common.ConceptInfoResponse;
import com.freediving.buddyservice.domain.query.common.DivingPoolInfoResponse;
import com.freediving.buddyservice.domain.query.common.ParticipantInfoResponse;
import com.freediving.buddyservice.domain.query.component.BuddyEventlistingCardResponse;
import com.freediving.common.config.annotation.UseCase;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetBuddyEventListingService implements GetBuddyEventListingUseCase {

	private final GetBuddyEventListingPort getBuddyEventListingPort;
	private final RequestMemberPort requestMemberPort;
	private final BuddyEventJoinPort buddyEventJoinPort;
	private final BuddyEventConceptMappingPort buddyEventConceptMappingPort;
	private final BuddyEventDivingPoolMappingPort buddyEventDivingPoolMappingPort;

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public QueryComponentListResponse getBuddyEventListing(Long userId,
		GetBuddyEventListingCommand command) {

		List<GetBuddyEventListingQueryProjectionDto> buddyEventListing = getBuddyEventListingPort.getBuddyEventListing(
			userId, command.getEventStartDate(), command.getEventEndDate(),
			command.getBuddyEventConcepts(), command.getCarShareYn(), command.getFreedivingLevel(),
			command.getDivingPools(), command.getSortType(), command.getGenderType(), command.getPageNumber(),
			command.getPageSize());

		if (buddyEventListing == null || buddyEventListing.isEmpty())
			throw new BuddyMeException(ServiceStatusCode.NO_CONTENT);

		Long totalCount = getBuddyEventListingPort.countOfGetBuddyEventListing(userId, command.getEventStartDate(),
			command.getEventEndDate(),
			command.getBuddyEventConcepts(), command.getCarShareYn(), command.getFreedivingLevel(),
			command.getDivingPools(), command.getGenderType());

		final List<Long> ids = buddyEventListing.stream()
			.map(e -> e.getEventId())
			.collect(Collectors.toList());

		Map<Long, List<BuddyEventDivingPoolMappingProjectDto>> allDivingPoolMappingByEventId = buddyEventDivingPoolMappingPort.getAllDivingPoolMapping(
			ids);

		Map<Long, List<BuddyEventConceptMappingProjectDto>> allConceptMappingByEventId = buddyEventConceptMappingPort.getAllConceptMapping(
			ids);

		Map<Long, List<BuddyEventJoinMappingProjectDto>> allJoinMappingByEventId = buddyEventJoinPort.getAllJoinMapping(
			ids);

		Set<Long> userIds = new HashSet<>();
		// 사용자 정보 요청할 사용자 ID 수집
		for (GetBuddyEventListingQueryProjectionDto event : buddyEventListing) {
			List<BuddyEventJoinMappingProjectDto> joinMappings = allJoinMappingByEventId.get(event.getEventId());

			// 사용자 정보 요청할 사용자 ID 수집
			joinMappings.stream()
				.filter(e -> (e.getStatus().equals(ParticipationStatus.OWNER) || e.getStatus()
					.equals(ParticipationStatus.PARTICIPATING)))
				.forEach(e -> userIds.add(e.getUserId()));
		}

		HashMap<Long, UserInfo> userHashMap = requestMemberPort.getMemberStatus(userIds.stream().toList());

		QueryComponentListResponse response = QueryComponentListResponse.builder()
			.components(new ArrayList<>())
			.totalCount(totalCount)
			.pageSize(
				command.getPageSize())
			.page(command.getPageNumber())
			.build();

		for (GetBuddyEventListingQueryProjectionDto event : buddyEventListing) {

			//작성자 정보를 못가져오면 제거.
			UserInfo buddyOwner = userHashMap.get(event.getUserId());
			if (buddyOwner == null)
				continue;

			List<BuddyEventConceptMappingProjectDto> conceptMappings = allConceptMappingByEventId.get(
				event.getEventId());
			List<BuddyEventDivingPoolMappingProjectDto> divingPoolMappings = allDivingPoolMappingByEventId.get(
				event.getEventId());
			List<BuddyEventJoinMappingProjectDto> joinMappings = allJoinMappingByEventId.get(event.getEventId());

			BuddyEventlistingCardResponse cardResponse = BuddyEventlistingCardResponse.builder()
				.userInfo(buddyOwner.toResponse())
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
					.filter(e -> (e.getStatus().equals(ParticipationStatus.PARTICIPATING)
						&& userHashMap.containsKey(e.getUserId())))
					.map(e -> ParticipantInfoResponse.builder().userId(e.getUserId())
						.profileImgUrl(userHashMap.get(e.getUserId()).getProfileImgUrl()).build())
					.collect(
						Collectors.toSet()))
				.genderType(event.getGenderType())
				.build();

			response.getComponents().add(cardResponse);
		}

		return response;
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public QueryComponentListResponse getLikeBuddyEventListing(Long userId, int pageNumber, int offset) {

		LocalDateTime now = LocalDateTime.now();
		List<GetBuddyEventListingQueryProjectionDto> buddyEventListing = getBuddyEventListingPort.getLikeBuddyEventListing(
			userId, now, pageNumber, offset);

		if (buddyEventListing == null || buddyEventListing.isEmpty())
			throw new BuddyMeException(ServiceStatusCode.NO_CONTENT);

		Long totalCount = getBuddyEventListingPort.countOfGetLikeBuddyEventListing(userId, now);

		final List<Long> ids = buddyEventListing.stream()
			.map(e -> e.getEventId())
			.collect(Collectors.toList());

		Map<Long, List<BuddyEventDivingPoolMappingProjectDto>> allDivingPoolMappingByEventId = buddyEventDivingPoolMappingPort.getAllDivingPoolMapping(
			ids);

		Map<Long, List<BuddyEventConceptMappingProjectDto>> allConceptMappingByEventId = buddyEventConceptMappingPort.getAllConceptMapping(
			ids);

		Map<Long, List<BuddyEventJoinMappingProjectDto>> allJoinMappingByEventId = buddyEventJoinPort.getAllJoinMapping(
			ids);

		Set<Long> userIds = new HashSet<>();
		// 사용자 정보 요청할 사용자 ID 수집
		for (GetBuddyEventListingQueryProjectionDto event : buddyEventListing) {
			List<BuddyEventJoinMappingProjectDto> joinMappings = allJoinMappingByEventId.get(event.getEventId());

			// 사용자 정보 요청할 사용자 ID 수집
			joinMappings.stream()
				.filter(e -> (e.getStatus().equals(ParticipationStatus.OWNER) || e.getStatus()
					.equals(ParticipationStatus.PARTICIPATING)))
				.forEach(e -> userIds.add(e.getUserId()));
		}

		HashMap<Long, UserInfo> userHashMap = requestMemberPort.getMemberStatus(userIds.stream().toList());

		QueryComponentListResponse response = QueryComponentListResponse.builder()
			.components(new ArrayList<>())
			.totalCount(totalCount)
			.pageSize(
				offset)
			.page(pageNumber)
			.build();

		for (GetBuddyEventListingQueryProjectionDto event : buddyEventListing) {

			//작성자 정보를 못가져오면 제거.
			UserInfo buddyOwner = userHashMap.get(event.getUserId());
			if (buddyOwner == null)
				continue;

			List<BuddyEventConceptMappingProjectDto> conceptMappings = allConceptMappingByEventId.get(
				event.getEventId());
			List<BuddyEventDivingPoolMappingProjectDto> divingPoolMappings = allDivingPoolMappingByEventId.get(
				event.getEventId());
			List<BuddyEventJoinMappingProjectDto> joinMappings = allJoinMappingByEventId.get(event.getEventId());

			BuddyEventlistingCardResponse cardResponse = BuddyEventlistingCardResponse.builder()
				.userInfo(buddyOwner.toResponse())
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
					.filter(e -> (e.getStatus().equals(ParticipationStatus.PARTICIPATING)
						&& userHashMap.containsKey(e.getUserId())))
					.map(e -> ParticipantInfoResponse.builder().userId(e.getUserId())
						.profileImgUrl(userHashMap.get(e.getUserId()).getProfileImgUrl()).build())
					.collect(
						Collectors.toSet()))
				.genderType(event.getGenderType())
				.build();

			response.getComponents().add(cardResponse);
		}

		return response;
	}

}
