package com.freediving.buddyservice.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.freediving.buddyservice.common.enumeration.EventConcept;
import com.freediving.buddyservice.common.enumeration.EventStatus;
import com.freediving.buddyservice.domain.CreatedBuddyEvent;

@ExtendWith(SpringExtension.class)
@Import(CreatedBuddyEventMapper.class)
class CreatedBuddyEventMapperTest {

	@Autowired
	private CreatedBuddyEventMapper createdBuddyEventMapper;

	@DisplayName("버디 일정 이벤트 JPA 객체를 버디 일정 이벤트 도메인 객체로 맵핑하여 생성한다.")
	@Test
	void mappingBuddyEventJpaToDomain() {
		Random random = new Random();
		Long userId = random.nextLong();

		LocalDateTime startTime = LocalDateTime.now();
		LocalDateTime endTime = LocalDateTime.now().plusHours(4);

		BuddyEventsJpaEntity buddyEventsJpaEntity = generateBuddyEventJpa(userId, startTime, endTime, 3, null);

		CreatedBuddyEvent createdBuddyEvent = createdBuddyEventMapper.mapToDomainEntity(buddyEventsJpaEntity);

		assertEquals(createdBuddyEvent.getEventId(), buddyEventsJpaEntity.getEventId());  // event Id
		assertEquals(createdBuddyEvent.getUserId(), buddyEventsJpaEntity.getUserId());  // user Id
		assertEquals(createdBuddyEvent.getEventStartDate(),
			buddyEventsJpaEntity.getEventStartTime());  // eventStartTime
		assertEquals(createdBuddyEvent.getEventEndDate(), buddyEventsJpaEntity.getEventEndTime());  // eventEndTime
		assertEquals(createdBuddyEvent.getEventConcepts(), buddyEventsJpaEntity.getEventConcepts());  //
		assertEquals(createdBuddyEvent.getStatus(), buddyEventsJpaEntity.getStatus());
		assertEquals(createdBuddyEvent.getCarShareYn(), buddyEventsJpaEntity.getCarShareYn());
		assertEquals(createdBuddyEvent.getParticipantCount(), buddyEventsJpaEntity.getParticipantCount());
		assertEquals(createdBuddyEvent.getComment(), buddyEventsJpaEntity.getComment());
		assertEquals(createdBuddyEvent.getCreatedDate(), buddyEventsJpaEntity.getCreatedDate());
		assertEquals(createdBuddyEvent.getUpdatedDate(), buddyEventsJpaEntity.getUpdatedDate());
	}

	private BuddyEventsJpaEntity generateBuddyEventJpa(Long userId, LocalDateTime eventStartDate,
		LocalDateTime eventEndDate,
		Integer participantCount, String comment) {

		List<EventConcept> eventConcepts = List.of(EventConcept.LEVEL_UP, EventConcept.PRACTICE);

		return BuddyEventsJpaEntity.builder()
			.userId(userId)
			.eventStartTime(eventStartDate)
			.eventEndTime(eventEndDate)
			.participantCount(participantCount)
			.eventConcepts(eventConcepts)
			.status(EventStatus.RECRUITING)
			.carShareYn(Boolean.FALSE)
			.comment(comment)
			.build();

	}
}