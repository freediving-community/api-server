package com.freediving.buddyservice.application.service.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.freediving.buddyservice.adapter.out.externalservice.member.userinfo.dto.UserInfo;
import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.BuddyEventConceptMappingProjectDto;
import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.BuddyEventDivingPoolMappingProjectDto;
import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.carousel.GetBuddyEventCarouselQueryProjectionDto;
import com.freediving.buddyservice.adapter.out.persistence.preference.UserDivingPoolEntity;
import com.freediving.buddyservice.adapter.out.persistence.preference.UserDivingPoolRepository;
import com.freediving.buddyservice.application.port.in.web.query.home.GetBuddyEventCarouselUseCase;
import com.freediving.buddyservice.application.port.in.web.query.home.GetHomeRecommendPoolBuddyEventCommand;
import com.freediving.buddyservice.application.port.in.web.query.home.GetHomeWeeklyBuddyEventCommand;
import com.freediving.buddyservice.application.port.out.externalservice.query.RequestMemberPort;
import com.freediving.buddyservice.application.port.out.web.query.GetBuddyEventCarouselPort;
import com.freediving.buddyservice.domain.query.QueryComponentListResponse;
import com.freediving.buddyservice.domain.query.QueryPreferencePoolCarouselResponse;
import com.freediving.buddyservice.domain.query.component.BuddyEventCarouselCardResponse;
import com.freediving.buddyservice.domain.query.component.common.ConceptInfoResponse;
import com.freediving.buddyservice.domain.query.component.common.DivingPoolInfoResponse;
import com.freediving.common.config.annotation.UseCase;
import com.freediving.common.enumerate.DivingPool;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetBuddyEventCarouselService implements GetBuddyEventCarouselUseCase {

	private final GetBuddyEventCarouselPort getBuddyEventCarouselPort;
	private final UserDivingPoolRepository userDivingPoolRepository;
	private final RequestMemberPort requestMemberPort;

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public QueryComponentListResponse getHomeWeekly(Long userId, GetHomeWeeklyBuddyEventCommand command) {

		List<GetBuddyEventCarouselQueryProjectionDto> buddyEventListing = getBuddyEventCarouselPort.getBuddyEventWeekly(
			userId, command.getEventStartDate(), command.getPageNumber(), command.getPageSize());

		if (buddyEventListing == null || buddyEventListing.isEmpty())
			throw new BuddyMeException(ServiceStatusCode.NO_CONTENT);

		Long totalCount = getBuddyEventCarouselPort.countOfGetBuddyEventWeekly(userId, command.getEventStartDate());

		final List<Long> ids = buddyEventListing.stream()
			.map(e -> e.getEventId())
			.collect(Collectors.toList());

		Map<Long, List<BuddyEventDivingPoolMappingProjectDto>> allDivingPoolMappingByEventId = getBuddyEventCarouselPort.getAllDivingPoolMapping(
			ids);

		Map<Long, List<BuddyEventConceptMappingProjectDto>> allConceptMappingByEventId = getBuddyEventCarouselPort.getAllConceptMapping(
			ids);

		Set<Long> userIds = new HashSet<>();
		// 사용자 정보 요청할 사용자 ID 수집
		for (GetBuddyEventCarouselQueryProjectionDto event : buddyEventListing) {
			// 사용자 정보 요청할 사용자 ID 수집
			userIds.add(event.getUserId());
		}

		HashMap<Long, UserInfo> userHashMap = requestMemberPort.getMemberStatus(userIds.stream().toList());

		QueryComponentListResponse response = QueryComponentListResponse.builder()
			.components(new ArrayList<>())
			.totalCount(totalCount)
			.pageSize(
				command.getPageSize())
			.page(command.getPageNumber())
			.build();

		for (GetBuddyEventCarouselQueryProjectionDto event : buddyEventListing) {
			List<BuddyEventConceptMappingProjectDto> conceptMappings = allConceptMappingByEventId.get(
				event.getEventId());
			List<BuddyEventDivingPoolMappingProjectDto> divingPoolMappings = allDivingPoolMappingByEventId.get(
				event.getEventId());

			BuddyEventCarouselCardResponse cardResponse = BuddyEventCarouselCardResponse.builder()
				.userInfo(userHashMap.get(event.getUserId()))
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
				.genderType(event.getGenderType())
				.build();

			response.getComponents().add(cardResponse);
		}

		return response;
	}

	@Override
	public QueryPreferencePoolCarouselResponse getHomeRecommendPoolBuddyEvent(Long userId,
		GetHomeRecommendPoolBuddyEventCommand command) {

		// 사용자 선호 다이빙 풀 조회.
		List<UserDivingPoolEntity> userDivingPools = userDivingPoolRepository.findAllByUserId(userId);
		DivingPool targetPool;
		if (userDivingPools == null || userDivingPools.isEmpty())
			throw new BuddyMeException(ServiceStatusCode.NO_CONTENT);

		// 타겟 풀장 선정.
		if (userDivingPools.size() == 1) {
			targetPool = userDivingPools.get(0).getDivingPoolId();
		} else {
			Random RANDOM = new Random();
			int randomIndex = RANDOM.nextInt(userDivingPools.size());
			targetPool = userDivingPools.get(randomIndex).getDivingPoolId();
		}

		List<GetBuddyEventCarouselQueryProjectionDto> buddyEventListing = getBuddyEventCarouselPort.getBuddyEventCarouselByDivingPool(
			userId, command.getEventStartDate(), targetPool);

		final List<Long> ids = buddyEventListing.stream()
			.map(e -> e.getEventId())
			.collect(Collectors.toList());

		Map<Long, List<BuddyEventDivingPoolMappingProjectDto>> allDivingPoolMappingByEventId = getBuddyEventCarouselPort.getAllDivingPoolMapping(
			ids);

		Map<Long, List<BuddyEventConceptMappingProjectDto>> allConceptMappingByEventId = getBuddyEventCarouselPort.getAllConceptMapping(
			ids);

		Set<Long> userIds = new HashSet<>();
		// 사용자 정보 요청할 사용자 ID 수집
		for (GetBuddyEventCarouselQueryProjectionDto event : buddyEventListing) {
			// 사용자 정보 요청할 사용자 ID 수집
			userIds.add(event.getUserId());
		}

		HashMap<Long, UserInfo> userHashMap = requestMemberPort.getMemberStatus(userIds.stream().toList());

		QueryPreferencePoolCarouselResponse response = QueryPreferencePoolCarouselResponse.builder()
			.components(new ArrayList<>())
			.title(targetPool.getDivingPoolName() + "에 같이 갈래요?")
			.divingPoolId(targetPool)
			.build();

		for (GetBuddyEventCarouselQueryProjectionDto event : buddyEventListing) {
			List<BuddyEventConceptMappingProjectDto> conceptMappings = allConceptMappingByEventId.get(
				event.getEventId());
			List<BuddyEventDivingPoolMappingProjectDto> divingPoolMappings = allDivingPoolMappingByEventId.get(
				event.getEventId());

			BuddyEventCarouselCardResponse cardResponse = BuddyEventCarouselCardResponse.builder()
				.userInfo(userHashMap.get(event.getUserId()))
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
				.genderType(event.getGenderType())
				.build();

			response.getComponents().add(cardResponse);
		}

		return response;
	}

}
