package com.freediving.buddyservice.adapter.out.persistence.event;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.freediving.buddyservice.adapter.out.persistence.event.concept.BuddyEventConceptMappingJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.divingpool.BuddyEventDivingPoolMappingJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.join.BuddyEventJoinRequestJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.listing.BuddyEventConceptMappingProjectDto;
import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.listing.BuddyEventDivingPoolMappingProjectDto;
import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.listing.BuddyEventJoinMappingProjectDto;
import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.listing.GetBuddyEventListingQueryProjectionDto;
import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.listing.GetBuddyEventListingRepoDSL;
import com.freediving.buddyservice.application.port.out.web.CreateBuddyEventPort;
import com.freediving.buddyservice.application.port.out.web.query.GetBuddyEventListingPort;
import com.freediving.buddyservice.config.enumerate.SortType;
import com.freediving.buddyservice.domain.command.CreatedBuddyEventResponse;
import com.freediving.buddyservice.domain.enumeration.BuddyEventConcept;
import com.freediving.buddyservice.domain.enumeration.ParticipationStatus;
import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.common.enumerate.DivingPool;

import lombok.RequiredArgsConstructor;

/**
 * Persistence 와의 연결을 담당하는 Adapter
 * 이 객체는 Persistence 와의 명령 수행만 담당한다. 비즈니스 적인 로직은 불가.
 *
 * @author 준희조
 * @version 1.0.0
 * 작성일 2023-12-27
 **/
@RequiredArgsConstructor
@PersistenceAdapter
public class BuddyEventPersistenceAdapter implements CreateBuddyEventPort, GetBuddyEventListingPort {

	private final BuddyEventRepository buddyEventRepository;
	private final GetBuddyEventListingRepoDSL getBuddyEventListingRepoDSL;

	@Override
	@Transactional
	public BuddyEventJpaEntity createBuddyEvent(CreatedBuddyEventResponse createdBuddyEventResponse) {

		// 1. 버디 이벤트 생성
		BuddyEventJpaEntity createdEventJpaEntity = buddyEventRepository.save(
			BuddyEventJpaEntity.builder()
				.userId(createdBuddyEventResponse.getUserId())
				.eventStartDate(createdBuddyEventResponse.getEventStartDate())
				.eventEndDate(createdBuddyEventResponse.getEventEndDate())
				.participantCount(createdBuddyEventResponse.getParticipantCount())
				.genderType(createdBuddyEventResponse.getGenderType())
				.carShareYn(createdBuddyEventResponse.getCarShareYn())
				.freedivingLevel(createdBuddyEventResponse.getFreedivingLevel())
				.status(createdBuddyEventResponse.getStatus())
				.kakaoRoomCode(createdBuddyEventResponse.getKakaoRoomCode())
				.comment(createdBuddyEventResponse.getComment())
				.build()
		);

		Set<BuddyEventDivingPoolMappingJpaEntity> buddyEventDivingPoolMappingJpaEntity = new HashSet<>();
		Set<BuddyEventJoinRequestJpaEntity> buddyEventJoinRequests = new HashSet<>();
		Set<BuddyEventConceptMappingJpaEntity> buddyEventConceptMappingJpaEntity = new HashSet<>();

		// 2. 다이빙 풀 연관 관계 설정
		for (DivingPool pool : createdBuddyEventResponse.getDivingPools())
			buddyEventDivingPoolMappingJpaEntity.add(
				BuddyEventDivingPoolMappingJpaEntity.builder().divingPoolId(pool).buddyEvent(createdEventJpaEntity)
					.build());

		// 참여 매핑
		buddyEventJoinRequests.add(BuddyEventJoinRequestJpaEntity.builder().userId(
				createdBuddyEventResponse.getUserId()).status(ParticipationStatus.OWNER)
			.buddyEvent(createdEventJpaEntity).build());

		// 이벤트 컨셉
		if (createdBuddyEventResponse.getBuddyEventConcepts() != null)
			for (BuddyEventConcept pool : createdBuddyEventResponse.getBuddyEventConcepts())
				buddyEventConceptMappingJpaEntity.add(
					BuddyEventConceptMappingJpaEntity.builder().conceptId(pool).buddyEvent(createdEventJpaEntity)
						.build());

		createdEventJpaEntity.changeBuddyEventDivingPoolMapping(buddyEventDivingPoolMappingJpaEntity);
		createdEventJpaEntity.changeBuddyEventJoinRequests(buddyEventJoinRequests);
		createdEventJpaEntity.changeBuddyEventConceptMapping(buddyEventConceptMappingJpaEntity);

		return createdEventJpaEntity;
	}

	@Override
	public List<GetBuddyEventListingQueryProjectionDto> getBuddyEventListing(Long userId, LocalDateTime eventStartDate,
		LocalDateTime eventEndDate, Set<BuddyEventConcept> buddyEventConcepts, Boolean carShareYn,
		Integer freedivingLevel, Set<DivingPool> divingPools, SortType sortType, int pageNumber, int pageSize) {
		// todo 고도화 필요.

		//eventId = {Long@15333} 1
		// eventStartDate = {LocalDateTime@15837} "2024-05-07T10:00"
		// eventEndDate = {LocalDateTime@15838} "2024-05-07T13:00"
		// isLiked = true
		// likedCount = {Integer@15635} 1
		// comment = "이번 모임은 캐주얼하게 진행합니다."
		// freedivingLevel = {Integer@15337} 0
		// status = {BuddyEventStatus@15637} "RECRUITING"
		// participantCount = {Integer@15645} 3
		// currentParticipantCount = {Long@15333} 1
		List<GetBuddyEventListingQueryProjectionDto> buddyEventListing = getBuddyEventListingRepoDSL.getBuddyEventListing(
			userId, eventStartDate, eventEndDate, buddyEventConcepts, carShareYn, freedivingLevel, divingPools,
			sortType, pageNumber, pageSize);

		return buddyEventListing;
	}

	@Override
	public Long countOfGetBuddyEventListing(Long userId, LocalDateTime eventStartDate,
		LocalDateTime eventEndDate, Set<BuddyEventConcept> buddyEventConcepts, Boolean carShareYn,
		Integer freedivingLevel, Set<DivingPool> divingPools, SortType sortType) {
		// todo 고도화 필요.

		//eventId = {Long@15333} 1
		// eventStartDate = {LocalDateTime@15837} "2024-05-07T10:00"
		// eventEndDate = {LocalDateTime@15838} "2024-05-07T13:00"
		// isLiked = true
		// likedCount = {Integer@15635} 1
		// comment = "이번 모임은 캐주얼하게 진행합니다."
		// freedivingLevel = {Integer@15337} 0
		// status = {BuddyEventStatus@15637} "RECRUITING"
		// participantCount = {Integer@15645} 3
		// currentParticipantCount = {Long@15333} 1
		Long count = getBuddyEventListingRepoDSL.countOfGetBuddyEventListing(
			userId, eventStartDate, eventEndDate, buddyEventConcepts, carShareYn, freedivingLevel, divingPools,
			sortType);

		return count;
	}

	public Map<Long, List<BuddyEventJoinMappingProjectDto>> getAllJoinMapping(List<Long> ids) {
		// 3. 참여자 User 정보 조회하기
		Map<Long, List<BuddyEventJoinMappingProjectDto>> allJoinMappingByEventId = getBuddyEventListingRepoDSL.findJoinMappingAllByEventIds(
			ids);
		return allJoinMappingByEventId;
	}

	public Map<Long, List<BuddyEventConceptMappingProjectDto>> getAllConceptMapping(List<Long> ids) {
		// 2.해당 이벤트 컨셉 조회하기
		Map<Long, List<BuddyEventConceptMappingProjectDto>> allConceptMappingByEventId = getBuddyEventListingRepoDSL.findConceptMappingAllByEventIds(
			ids);
		return allConceptMappingByEventId;
	}

	public Map<Long, List<BuddyEventDivingPoolMappingProjectDto>> getAllDivingPoolMapping(List<Long> ids) {
		// 1.해당 이벤트 다이빙 풀 조회하기

		Map<Long, List<BuddyEventDivingPoolMappingProjectDto>> allDivingPoolMappingByEventId = getBuddyEventListingRepoDSL.findDivingPoolMappingAllByEventIds(
			ids);
		return allDivingPoolMappingByEventId;
	}
}
