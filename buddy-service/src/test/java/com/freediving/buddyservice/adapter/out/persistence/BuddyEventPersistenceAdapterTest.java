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
import com.freediving.buddyservice.adapter.out.persistence.event.divingpool.BuddyEventDivingPoolMappingRepository;
import com.freediving.buddyservice.adapter.out.persistence.event.join.BuddyEventJoinRequestRepository;
import com.freediving.buddyservice.adapter.out.persistence.event.likecount.BuddyEventLikeCountRepository;
import com.freediving.buddyservice.adapter.out.persistence.event.likecount.BuddyEventLikeMappingRepository;
import com.freediving.buddyservice.adapter.out.persistence.event.viewcount.BuddyEventViewCountRepository;
import com.freediving.buddyservice.application.port.out.web.createBuddyEventPort;
import com.freediving.buddyservice.config.enumerate.GenderType;
import com.freediving.buddyservice.domain.command.CreatedBuddyEventResponse;
import com.freediving.buddyservice.domain.enumeration.BuddyEventStatus;
import com.freediving.common.enumerate.DivingPool;

@ActiveProfiles("local")
@SpringBootTest
class BuddyEventPersistenceAdapterTest {

	@Autowired
	createBuddyEventPort createBuddyEventPort;
	@Autowired
	BuddyEventRepository buddyEventRepository;
	@Autowired
	BuddyEventLikeCountRepository buddyEventLikeCountRepository;
	@Autowired
	BuddyEventViewCountRepository buddyEventViewCountRepository;
	@Autowired
	BuddyEventConceptMappingRepository buddyEventConceptMappingRepository;
	@Autowired
	BuddyEventDivingPoolMappingRepository buddyEventDivingPoolMappingRepository;
	@Autowired
	BuddyEventJoinRequestRepository buddyEventJoinRequestRepository;
	@Autowired
	BuddyEventLikeMappingRepository buddyEventLikeMappingRepository;

	@AfterEach
	void tearDown() {
		buddyEventConceptMappingRepository.deleteAllInBatch();
		buddyEventDivingPoolMappingRepository.deleteAllInBatch();
		buddyEventJoinRequestRepository.deleteAllInBatch();
		buddyEventLikeCountRepository.deleteAllInBatch();
		buddyEventViewCountRepository.deleteAllInBatch();
		buddyEventLikeMappingRepository.deleteAllInBatch();
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
			DivingPool.PARADIVE));

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
			.genderType(GenderType.ALL)
			.comment(comment)
			.freedivingLevel(freedivingLevel)
			.divingPools(divingPool)
			.build();

	}

}