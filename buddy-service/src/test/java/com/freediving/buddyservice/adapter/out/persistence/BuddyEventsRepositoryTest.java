package com.freediving.buddyservice.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.freediving.buddyservice.common.enumeration.EventConcept;
import com.freediving.buddyservice.common.enumeration.EventStatus;

@ActiveProfiles("local")
@DataJpaTest // JPA관련된 Bean만 주입받아 테스트 가능.
class BuddyEventsRepositoryTest {

	@Autowired
	private BuddyEventsRepository buddyEventsRepository;

	@AfterEach
	void tearDown() {
		buddyEventsRepository.deleteAllInBatch();
	}

	@DisplayName("버디 일정 이벤트를 생성 한다.")
	@Test
	void createBuddyEvent() {
		Random random = new Random();
		Long userId = random.nextLong();

		LocalDateTime startTime = LocalDateTime.now();
		LocalDateTime endTime = LocalDateTime.now().plusHours(4);

		BuddyEventsJpaEntity buddyEventsJpaEntity = generateBuddyEventJpa(userId, startTime, endTime, 3, null);

		buddyEventsRepository.save(buddyEventsJpaEntity);

		List<BuddyEventsJpaEntity> buddyEvents = buddyEventsRepository.findAll();

		assertThat(buddyEvents).hasSize(1)
			.extracting("eventId", "userId", "eventStartTime", "eventEndTime", "participantCount", "eventConcepts",
				"status",
				"carShareYn", "comment")
			.containsExactlyInAnyOrder(
				tuple(1L, userId, startTime, endTime, 3, List.of(EventConcept.LEVEL_UP, EventConcept.PRACTICE),
					EventStatus.RECRUITING, false,
					null)
			);
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