package com.freediving.buddyservice.adapter.out.persistence.event;

import java.util.HashSet;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.freediving.buddyservice.adapter.out.persistence.event.concept.BuddyEventConceptMappingJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.divingpool.BuddyEventDivingPoolMappingJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.join.BuddyEventJoinRequestJpaEntity;
import com.freediving.buddyservice.application.port.out.web.CreateBuddyEventPort;
import com.freediving.buddyservice.common.enumeration.BuddyEventConcept;
import com.freediving.buddyservice.common.enumeration.ParticipationStatus;
import com.freediving.buddyservice.domain.command.CreatedBuddyEventResponse;
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
public class BuddyEventPersistenceAdapter implements CreateBuddyEventPort {

	private final BuddyEventRepository buddyEventRepository;

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

}
