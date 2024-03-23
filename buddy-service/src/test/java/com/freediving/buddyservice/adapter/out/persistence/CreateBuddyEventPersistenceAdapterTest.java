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

import com.freediving.buddyservice.adapter.out.persistence.event.BuddyEventJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.BuddyEventRepository;
import com.freediving.buddyservice.application.port.out.CreateBuddyEventPort;
import com.freediving.buddyservice.common.enumeration.BuddyEventConcept;
import com.freediving.buddyservice.common.enumeration.BuddyEventStatus;
import com.freediving.buddyservice.domain.CreatedBuddyEventResponse;

@ActiveProfiles("local")
@SpringBootTest
class CreateBuddyEventPersistenceAdapterTest {

	@Autowired
	CreateBuddyEventPort createBuddyEventPort;
	@Autowired
	BuddyEventRepository buddyEventRepository;

	@AfterEach
	void tearDown() {
		buddyEventRepository.deleteAllInBatch();
	}

	@DisplayName("PersistenceAdapter 역할로서 버디 일정 생성의 연결을 테스트한다.")
	@Test
	void createBuddyEvent() {

		Random random = new Random();
		Long userId = random.nextLong();

		LocalDateTime StartDate = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
		LocalDateTime EndDate = LocalDateTime.now().plusHours(4).truncatedTo(ChronoUnit.MILLIS);

		CreatedBuddyEventResponse buddyEvent = generateBuddyEvent(userId, StartDate, EndDate, 3, null);

		BuddyEventJpaEntity createdbuddyEvent = createBuddyEventPort.createBuddyEvent(buddyEvent);

		assertThat(createdbuddyEvent)
			.extracting("userId", "eventStartDate", "eventEndDate", "participantCount", "buddyEventConcepts",
				"status", "carShareYn", "comment")
			.contains(userId, StartDate.truncatedTo(ChronoUnit.MILLIS), EndDate.truncatedTo(ChronoUnit.MILLIS), 3,
				List.of(BuddyEventConcept.LEVEL_UP, BuddyEventConcept.PRACTICE),
				BuddyEventStatus.RECRUITING, false, null);

	}

	private CreatedBuddyEventResponse generateBuddyEvent(Long userId, LocalDateTime eventStartDate, LocalDateTime eventEndDate,
		Integer participantCount, String comment) {

		List<BuddyEventConcept> buddyEventConcepts = List.of(BuddyEventConcept.LEVEL_UP, BuddyEventConcept.PRACTICE);

		return CreatedBuddyEventResponse.builder()
			.userId(userId)
			.eventStartDate(eventStartDate)
			.eventEndDate(eventEndDate)
			.participantCount(participantCount)
			.carShareYn(Boolean.FALSE)
			.status(BuddyEventStatus.RECRUITING)
			.comment(comment)
			.build();

	}

}