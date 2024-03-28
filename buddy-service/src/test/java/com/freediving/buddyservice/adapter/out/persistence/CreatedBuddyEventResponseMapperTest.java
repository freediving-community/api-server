package com.freediving.buddyservice.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.freediving.buddyservice.adapter.out.persistence.event.BuddyEventJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.CreatedBuddyEventResponseMapper;
import com.freediving.buddyservice.common.enumeration.BuddyEventStatus;
import com.freediving.buddyservice.domain.CreatedBuddyEventResponse;

@ExtendWith(SpringExtension.class)
@Import(CreatedBuddyEventResponseMapper.class)
class CreatedBuddyEventResponseMapperTest {

	@Autowired
	private CreatedBuddyEventResponseMapper createdBuddyEventResponseMapper;

	@DisplayName("버디 일정 이벤트 JPA 객체를 버디 일정 이벤트 도메인 객체로 맵핑하여 생성한다.")
	@Test
	void mappingBuddyEventJpaToDomain() {
		Random random = new Random();
		Long userId = random.nextLong();

		LocalDateTime StartDate = LocalDateTime.now();
		LocalDateTime EndDate = LocalDateTime.now().plusHours(4);

		BuddyEventJpaEntity buddyEventJpaEntity = generateBuddyEventJpa(userId, StartDate, EndDate, 3, null);

		CreatedBuddyEventResponse createdBuddyEventResponse = createdBuddyEventResponseMapper.mapToDomainEntity(
			buddyEventJpaEntity);

		assertThat(createdBuddyEventResponse)
			.extracting("eventId", "userId", "eventStartDate", "eventEndDate",
				"participantCount", "status", "carShareYn", "comment")
			.contains(buddyEventJpaEntity.getEventId(), buddyEventJpaEntity.getUserId(),
				buddyEventJpaEntity.getEventStartDate(), buddyEventJpaEntity.getEventEndDate(),
				buddyEventJpaEntity.getStatus(),
				buddyEventJpaEntity.getCarShareYn(), buddyEventJpaEntity.getParticipantCount(),
				buddyEventJpaEntity.getComment(), buddyEventJpaEntity.getCreatedDate(),
				buddyEventJpaEntity.getUpdatedDate());
	}

	private BuddyEventJpaEntity generateBuddyEventJpa(Long userId, LocalDateTime eventStartDate,
		LocalDateTime eventEndDate,
		Integer participantCount, String comment) {

		return BuddyEventJpaEntity.builder()
			.userId(userId)
			.eventStartDate(eventStartDate)
			.eventEndDate(eventEndDate)
			.participantCount(participantCount)
			.status(BuddyEventStatus.RECRUITING)
			.carShareYn(Boolean.FALSE)
			.comment(comment)
			.build();

	}
}