package com.freediving.buddyservice.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.freediving.buddyservice.application.port.out.CreateBuddyEventPort;
import com.freediving.buddyservice.common.enumeration.EventConcept;
import com.freediving.buddyservice.common.enumeration.EventStatus;
import com.freediving.buddyservice.domain.CreatedBuddyEvent;

@ActiveProfiles("local")
@SpringBootTest
class CreateBuddyEventPersistenceAdapterTest {

	@Autowired
	CreateBuddyEventPort createBuddyEventPort;
	@Autowired
	BuddyEventsRepository buddyEventsRepository;

	@AfterEach
	void tearDown() {
		buddyEventsRepository.deleteAllInBatch();
	}

	@DisplayName("PersistenceAdapter 역할로서 버디 일정 생성의 연결을 테스트한다.")
	@Test
	void createBuddyEvent() {

		Random random = new Random();
		Long userId = random.nextLong();

		LocalDateTime startTime = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
		LocalDateTime endTime = LocalDateTime.now().plusHours(4).truncatedTo(ChronoUnit.MILLIS);

		CreatedBuddyEvent buddyEvent = generateBuddyEvent(userId, startTime, endTime, 3, null);

		createBuddyEventPort.createBuddyEvent(buddyEvent);

		List<BuddyEventsJpaEntity> buddyEvents = buddyEventsRepository.findAll();

		assertThat(buddyEvents).hasSize(1)
			.extracting("eventId", "userId", "eventStartTime", "eventEndTime", "participantCount", "eventConcepts",
				"status", "carShareYn", "comment")
			.containsExactlyInAnyOrder(
				tuple(1L, userId, startTime.truncatedTo(ChronoUnit.MILLIS), endTime.truncatedTo(ChronoUnit.MILLIS), 3,
					List.of(EventConcept.LEVEL_UP, EventConcept.PRACTICE),
					EventStatus.RECRUITING, false,
					null)
			);

	}

	private CreatedBuddyEvent generateBuddyEvent(Long userId, LocalDateTime eventStartDate, LocalDateTime eventEndDate,
		Integer participantCount, String comment) {

		List<EventConcept> eventConcepts = List.of(EventConcept.LEVEL_UP, EventConcept.PRACTICE);

		return CreatedBuddyEvent.builder()
			.userId(userId)
			.eventStartDate(eventStartDate)
			.eventEndDate(eventEndDate)
			.participantCount(participantCount)
			.eventConcepts(eventConcepts)
			.carShareYn(Boolean.FALSE)
			.status(EventStatus.RECRUITING)
			.comment(comment)
			.build();

	}

}