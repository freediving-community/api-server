package com.freediving.buddyservice.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

	@DisplayName("버디 일정 이벤트를 생성한다.")
	@Test
	void createBuddyEvent() {
		Random random = new Random();
		Long userId = random.nextLong();

		LocalDateTime StartDate = LocalDateTime.now();
		LocalDateTime EndDate = LocalDateTime.now().plusHours(4);

		BuddyEventsJpaEntity buddyEventsJpaEntity = generateBuddyEventJpa(userId, StartDate, EndDate, 3, null,
			EventStatus.RECRUITING);

		BuddyEventsJpaEntity createdBuddyEventJps = buddyEventsRepository.save(buddyEventsJpaEntity);

		assertThat(createdBuddyEventJps).extracting("eventId", "userId", "eventStartDate", "eventEndDate",
				"participantCount", "eventConcepts", "status", "carShareYn", "comment")
			.contains(createdBuddyEventJps.getEventId(), userId, StartDate, EndDate, 3,
				List.of(EventConcept.LEVEL_UP, EventConcept.PRACTICE),
				EventStatus.RECRUITING, false, null);
	}

	@DisplayName("버디 이벤트 생성시 이미 모집 중인 이벤트와 시작 시간이 겹치는 경우 True.")
	@Test
	void shouldTrueToCreateEventWhenStartTimeOverlapsWithExistingEvent() {
		// given
		Random random = new Random();
		Long userId = random.nextLong();

		// 14시~18시
		LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 14, 00, 00);
		LocalDateTime endDate = LocalDateTime.of(2024, 1, 1, 18, 00, 00);

		BuddyEventsJpaEntity buddyEventsJpaEntity = generateBuddyEventJpa(userId, startDate, endDate, 3, null,
			EventStatus.RECRUITING);
		buddyEventsRepository.save(buddyEventsJpaEntity);

		// 10시 ~ 14시 1초
		LocalDateTime createStartDate = LocalDateTime.of(2024, 1, 1, 10, 00, 00);
		LocalDateTime createEndDate = LocalDateTime.of(2024, 1, 1, 14, 00, 01);

		List<String> statusNames = List.of(EventStatus.RECRUITING, EventStatus.RECRUITMENT_CLOSED).stream()
			.map(Enum::name)
			.collect(Collectors.toList());

		// when
		boolean result = buddyEventsRepository.existsBuddyEventByEventTime(userId, createStartDate, createEndDate,
			statusNames);

		// then
		assertThat(result).isTrue();

	}

	@DisplayName("버디 이벤트 생성시 이미 모집 중인 이벤트와 종료 시간이 겹치는 경우 True.")
	@Test
	void shouldTrueToCreateEventWhenEndTimeOverlapsWithExistingEvent() {
		// given
		Random random = new Random();
		Long userId = random.nextLong();

		// 14시~18시
		LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 14, 00, 00);
		LocalDateTime endDate = LocalDateTime.of(2024, 1, 1, 18, 00, 00);

		BuddyEventsJpaEntity buddyEventsJpaEntity = generateBuddyEventJpa(userId, startDate, endDate, 3, null,
			EventStatus.RECRUITING);
		buddyEventsRepository.save(buddyEventsJpaEntity);

		// 10시 ~ 14시 1초
		LocalDateTime createStartDate = LocalDateTime.of(2024, 1, 1, 17, 59, 59);
		LocalDateTime createEndDate = LocalDateTime.of(2024, 1, 1, 20, 00, 00);

		List<String> statusNames = List.of(EventStatus.RECRUITING, EventStatus.RECRUITMENT_CLOSED).stream()
			.map(Enum::name)
			.collect(Collectors.toList());

		// when
		boolean result = buddyEventsRepository.existsBuddyEventByEventTime(userId, createStartDate, createEndDate,
			statusNames);

		// then
		assertThat(result).isTrue();

	}

	@DisplayName("버디 이벤트 생성시 이미 모집 중인 이벤트와 시간이 겹치는 경우 True.")
	@Test
	void shouldTrueToCreateEventWhenTimeConflictsWithExistingEvent() {
		// given
		Random random = new Random();
		Long userId = random.nextLong();

		// 14시~18시
		LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 14, 00, 00);
		LocalDateTime endDate = LocalDateTime.of(2024, 1, 1, 18, 00, 00);

		BuddyEventsJpaEntity buddyEventsJpaEntity = generateBuddyEventJpa(userId, startDate, endDate, 3, null,
			EventStatus.RECRUITING);
		buddyEventsRepository.save(buddyEventsJpaEntity);

		// 10시 ~ 14시 1초
		LocalDateTime createStartDate = LocalDateTime.of(2024, 1, 1, 14, 00, 01);
		LocalDateTime createEndDate = LocalDateTime.of(2024, 1, 1, 17, 59, 59);

		List<String> statusNames = List.of(EventStatus.RECRUITING, EventStatus.RECRUITMENT_CLOSED).stream()
			.map(Enum::name)
			.collect(Collectors.toList());

		// when
		boolean result = buddyEventsRepository.existsBuddyEventByEventTime(userId, createStartDate, createEndDate,
			statusNames);

		// then
		assertThat(result).isTrue();

	}

	@DisplayName("버디 이벤트 생성시 이미 모집 중인 이벤트와 시간이 겹치지 않는 경우 False.")
	@Test
	void shouldFalseToCreateEventWhenTimeConflictsWithExistingEvent() {
		// given
		Random random = new Random();
		Long userId = random.nextLong();

		// 14시~18시
		LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 14, 00, 00);
		LocalDateTime endDate = LocalDateTime.of(2024, 1, 1, 18, 00, 00);

		BuddyEventsJpaEntity buddyEventsJpaEntity = generateBuddyEventJpa(userId, startDate, endDate, 3, null,
			EventStatus.RECRUITING);
		buddyEventsRepository.save(buddyEventsJpaEntity);

		// 10시 ~ 14시 1초
		LocalDateTime createStartDate = LocalDateTime.of(2024, 1, 1, 10, 00, 00);
		LocalDateTime createEndDate = LocalDateTime.of(2024, 1, 1, 14, 00, 00);

		List<String> statusNames = List.of(EventStatus.RECRUITING, EventStatus.RECRUITMENT_CLOSED).stream()
			.map(Enum::name)
			.collect(Collectors.toList());

		// when
		boolean result = buddyEventsRepository.existsBuddyEventByEventTime(userId, createStartDate, createEndDate,
			statusNames);

		// then
		assertThat(result).isFalse();

	}

	@DisplayName("버디 이벤트 생성시 이미 모집 중인 이벤트와 시간이 겹쳐도 삭제된 이벤트는 상관없다.")
	@Test
	void shouldAllowCreationWhenEventsOverlapWithDeletedEvents() {
		// given
		Random random = new Random();
		Long userId = random.nextLong();

		// 14시~18시
		LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 14, 00, 00);
		LocalDateTime endDate = LocalDateTime.of(2024, 1, 1, 18, 00, 00);

		BuddyEventsJpaEntity buddyEventsJpaEntity = generateBuddyEventJpa(userId, startDate, endDate, 3, null,
			EventStatus.RECRUITMENT_DELETED);
		buddyEventsJpaEntity = buddyEventsRepository.save(buddyEventsJpaEntity);

		// 10시 ~ 14시 1초
		LocalDateTime createStartDate = LocalDateTime.of(2024, 1, 1, 15, 00, 00);
		LocalDateTime createEndDate = LocalDateTime.of(2024, 1, 1, 20, 00, 00);

		List<String> statusNames = List.of(EventStatus.RECRUITING, EventStatus.RECRUITMENT_CLOSED).stream()
			.map(Enum::name)
			.collect(Collectors.toList());

		// when
		boolean result = buddyEventsRepository.existsBuddyEventByEventTime(userId, createStartDate, createEndDate,
			statusNames);

		// then
		assertThat(result).isFalse();

	}

	private BuddyEventsJpaEntity generateBuddyEventJpa(Long userId, LocalDateTime eventStartDate,
		LocalDateTime eventEndDate, Integer participantCount, String comment, EventStatus status) {

		List<EventConcept> eventConcepts = List.of(EventConcept.LEVEL_UP, EventConcept.PRACTICE);

		return BuddyEventsJpaEntity.builder()
			.userId(userId)
			.eventStartDate(eventStartDate)
			.eventEndDate(eventEndDate)
			.participantCount(participantCount)
			.eventConcepts(eventConcepts)
			.status(status)
			.carShareYn(Boolean.FALSE)
			.comment(comment)
			.build();

	}

}