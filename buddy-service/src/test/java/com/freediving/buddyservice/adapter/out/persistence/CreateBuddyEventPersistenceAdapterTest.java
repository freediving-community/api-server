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

		LocalDateTime StartDate = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
		LocalDateTime EndDate = LocalDateTime.now().plusHours(4).truncatedTo(ChronoUnit.MILLIS);

		CreatedBuddyEvent buddyEvent = generateBuddyEvent(userId, StartDate, EndDate, 3, null);

		BuddyEventsJpaEntity createdbuddyEvent = createBuddyEventPort.createBuddyEvent(buddyEvent);

		assertThat(createdbuddyEvent)
			.extracting("userId", "eventStartDate", "eventEndDate", "participantCount", "eventConcepts",
				"status", "carShareYn", "comment")
			.contains(userId, StartDate.truncatedTo(ChronoUnit.MILLIS), EndDate.truncatedTo(ChronoUnit.MILLIS), 3,
				List.of(EventConcept.LEVEL_UP, EventConcept.PRACTICE),
				EventStatus.RECRUITING, false, null);

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