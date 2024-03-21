package com.freediving.buddyservice.adapter.out.persistence.event;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.freediving.buddyservice.application.port.out.CreateBuddyEventPort;
import com.freediving.buddyservice.common.enumeration.EventConcept;
import com.freediving.buddyservice.common.enumeration.ParticipationStatus;
import com.freediving.buddyservice.domain.CreatedBuddyEvent;
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
public class CreateBuddyEventPersistenceAdapter implements CreateBuddyEventPort {

	private final BuddyEventsRepository buddyEventsRepository;

	@Override
	@Transactional
	public BuddyEventsJpaEntity createBuddyEvent(CreatedBuddyEvent createdBuddyEvent) {

		// 1. 버디 이벤트 생성
		BuddyEventsJpaEntity createdEventJpaEntity = buddyEventsRepository.save(
			BuddyEventsJpaEntity.builder()
				.userId(createdBuddyEvent.getUserId())
				.eventStartDate(createdBuddyEvent.getEventStartDate())
				.eventEndDate(createdBuddyEvent.getEventEndDate())
				.participantCount(createdBuddyEvent.getParticipantCount())
				.carShareYn(createdBuddyEvent.getCarShareYn())
				.status(createdBuddyEvent.getStatus())
				.kakaoRoomCode(createdBuddyEvent.getKakaoRoomCode())
				.comment(createdBuddyEvent.getComment())
				.build()
		);

		Set<EventsDivingPoolMapping> eventsDivingPoolMapping = new HashSet<>();
		BuddyEventConditions buddyEventCondition = null;
		Set<BuddyEventJoinRequests> buddyEventJoinRequests = new HashSet<>();
		Set<EventsConceptMapping> eventsConceptMapping = new HashSet<>();

		// 2. 다이빙 풀 연관 관계 설정
		for (DivingPool pool : createdBuddyEvent.getDivingPools())
			eventsDivingPoolMapping.add(
				EventsDivingPoolMapping.builder().divingPoolId(pool).buddyEvent(createdEventJpaEntity)
					.build());

		// 참여 매핑
		buddyEventJoinRequests.add(BuddyEventJoinRequests.builder().userId(
				createdBuddyEvent.getUserId()).status(ParticipationStatus.OWNER)
			.buddyEvent(createdEventJpaEntity).build());

		// 레벨 조건 존재안하면 null , //TODO 레벨 조건 존재안하면 null 테스트 케이스
		buddyEventCondition = BuddyEventConditions.builder().buddyEvent(createdEventJpaEntity)
			.freedivingLevel(createdBuddyEvent.getFreedivingLevel())
			.build();

		// 이벤트 컨셉
		if (createdBuddyEvent.getEventConcepts() != null)
			for (EventConcept pool : createdBuddyEvent.getEventConcepts())
				eventsConceptMapping.add(
					EventsConceptMapping.builder().conceptId(pool).buddyEvent(createdEventJpaEntity)
						.build());

		createdEventJpaEntity.changeEventsDivingPoolMapping(eventsDivingPoolMapping);
		createdEventJpaEntity.changeBuddyEventConditions(buddyEventCondition);
		createdEventJpaEntity.changeBuddyEventJoinRequests(buddyEventJoinRequests);
		createdEventJpaEntity.changeEventsConceptMapping(eventsConceptMapping);

		return createdEventJpaEntity;
	}

	@Override
	public Boolean isValidBuddyEventOverlap(Long userId, LocalDateTime eventStartDate, LocalDateTime eventEndTime,
		List<String> statuses) {
		if (buddyEventsRepository.existsBuddyEventByEventTime(userId, eventStartDate
			, eventEndTime, statuses) == true)
			return false;

		return true;

	}

}
