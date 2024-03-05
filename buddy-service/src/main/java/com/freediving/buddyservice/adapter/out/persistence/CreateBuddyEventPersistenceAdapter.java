package com.freediving.buddyservice.adapter.out.persistence;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.freediving.buddyservice.application.port.out.CreateBuddyEventPort;
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
	public BuddyEventsJpaEntity createBuddyEvent(CreatedBuddyEvent createdBuddyEvent) {

		Set<EventsDivingPoolMapping> eventsDivingPoolMapping = new HashSet<>();
		Set<BuddyEventConditions> buddyEventConditions = null;
		Set<BuddyEventJoinRequests> buddyEventJoinRequests = new HashSet<>();

		// 다이빙풀
		for (DivingPool pool : createdBuddyEvent.getDivingPools())
			eventsDivingPoolMapping.add(
				EventsDivingPoolMapping.builder().divingPoolId(pool)
					.build());

		// 참여 매핑
		buddyEventJoinRequests.add(BuddyEventJoinRequests.builder().userId(
			createdBuddyEvent.getUserId()).status(ParticipationStatus.OWNER).build());

		// 조건 매팽
		// 레벨 조건 존재하면
		if (createdBuddyEvent.getFreedivingLevel() != null) {
			buddyEventConditions = new HashSet<>();
			buddyEventConditions.add(
				BuddyEventConditions.builder().freedivingLevel(createdBuddyEvent.getFreedivingLevel()).build());

		}

		return buddyEventsRepository.save(
			BuddyEventsJpaEntity.builder()
				.userId(createdBuddyEvent.getUserId())
				.eventStartDate(createdBuddyEvent.getEventStartDate())
				.eventEndDate(createdBuddyEvent.getEventEndDate())
				.participantCount(createdBuddyEvent.getParticipantCount())
				.eventConcepts(createdBuddyEvent.getEventConcepts())
				.carShareYn(createdBuddyEvent.getCarShareYn())
				.status(createdBuddyEvent.getStatus())
				.kakaoRoomCode(createdBuddyEvent.getKakaoRoomCode())
				.comment(createdBuddyEvent.getComment())
				.buddyEventConditions(buddyEventConditions)
				.buddyEventJoinRequests(buddyEventJoinRequests)
				.eventsDivingPoolMapping(eventsDivingPoolMapping)
				.build()
		);

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
