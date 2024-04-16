package com.freediving.buddyservice.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.freediving.buddyservice.adapter.out.persistence.event.BuddyEventJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.BuddyEventRepository;
import com.freediving.buddyservice.adapter.out.persistence.event.concept.BuddyEventConceptMappingRepository;
import com.freediving.buddyservice.adapter.out.persistence.event.condition.BuddyEventConditionsRepository;
import com.freediving.buddyservice.adapter.out.persistence.event.divingpool.BuddyEventDivingPoolMappingRepository;
import com.freediving.buddyservice.adapter.out.persistence.event.join.BuddyEventJoinRequestRepository;
import com.freediving.buddyservice.application.port.out.web.CreateBuddyEventPort;
import com.freediving.buddyservice.common.enumeration.BuddyEventStatus;
import com.freediving.buddyservice.domain.command.CreatedBuddyEventResponse;
import com.freediving.common.enumerate.DivingPool;

@ActiveProfiles("local")
@SpringBootTest
class BuddyEventPersistenceAdapterTest {

	@Autowired
	CreateBuddyEventPort createBuddyEventPort;
	@Autowired
	BuddyEventRepository buddyEventRepository;
	@Autowired
	BuddyEventConceptMappingRepository buddyEventConceptMappingRepository;
	@Autowired
	BuddyEventConditionsRepository buddyEventConditionsRepository;
	@Autowired
	BuddyEventDivingPoolMappingRepository buddyEventDivingPoolMappingRepository;
	@Autowired
	BuddyEventJoinRequestRepository buddyEventJoinRequestRepository;

	@AfterEach
	void tearDown() {
		buddyEventConceptMappingRepository.deleteAllInBatch();
		buddyEventConditionsRepository.deleteAllInBatch();
		buddyEventDivingPoolMappingRepository.deleteAllInBatch();
		buddyEventJoinRequestRepository.deleteAllInBatch();

		buddyEventRepository.deleteAllInBatch();
	}

	@DisplayName("PersistenceAdapter 역할로서 버디 일정 생성의 연결을 테스트한다.")
	@Test
	void createBuddyEvent() {

		Random random = new Random();
		Long userId = random.nextLong();

		LocalDateTime StartDate = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
		LocalDateTime EndDate = LocalDateTime.now().plusHours(4).truncatedTo(ChronoUnit.MILLIS);

		CreatedBuddyEventResponse buddyEvent = generateBuddyEvent(userId, StartDate, EndDate, 3, null, 2, Set.of(
			DivingPool.JAMSIL_DIVING_POOL));

		BuddyEventJpaEntity createdbuddyEvent = createBuddyEventPort.createBuddyEvent(buddyEvent);

		assertThat(createdbuddyEvent)
			.extracting("userId", "eventStartDate", "eventEndDate", "participantCount",
				"status", "carShareYn", "comment")
			.contains(userId, StartDate.truncatedTo(ChronoUnit.MILLIS), EndDate.truncatedTo(ChronoUnit.MILLIS), 3,
				BuddyEventStatus.RECRUITING, false, null);

	}

	private CreatedBuddyEventResponse generateBuddyEvent(Long userId, LocalDateTime eventStartDate,
		LocalDateTime eventEndDate,
		Integer participantCount, String comment, Integer freedivingLevel, Set divingPool) {

		return CreatedBuddyEventResponse.builder()
			.userId(userId)
			.eventStartDate(eventStartDate)
			.eventEndDate(eventEndDate)
			.participantCount(participantCount)
			.carShareYn(Boolean.FALSE)
			.status(BuddyEventStatus.RECRUITING)
			.comment(comment)
			.freedivingLevel(freedivingLevel)
			.divingPools(divingPool)
			.build();

	}

}