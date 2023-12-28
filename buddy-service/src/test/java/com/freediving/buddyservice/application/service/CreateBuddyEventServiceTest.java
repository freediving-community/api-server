package com.freediving.buddyservice.application.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.freediving.buddyservice.adapter.out.persistence.BuddyEventsRepository;
import com.freediving.buddyservice.application.port.in.CreateBuddyEventCommand;
import com.freediving.buddyservice.application.port.in.CreateBuddyEventUseCase;
import com.freediving.buddyservice.application.port.out.service.MemberStatus;
import com.freediving.buddyservice.application.port.out.service.RequestMemberPort;
import com.freediving.buddyservice.common.enumeration.EventConcept;

@SpringBootTest
@ActiveProfiles("local")
class CreateBuddyEventServiceTest {

	@Autowired
	private CreateBuddyEventUseCase createBuddyEventUseCase;

	@Autowired
	private BuddyEventsRepository buddyEventsRepository;

	@MockBean
	private RequestMemberPort requestMemberPort;

	@AfterEach
	void tearDown() {
		buddyEventsRepository.deleteAllInBatch();
	}

	@DisplayName("비정상적인 사용자의 버디 이벤트 생성 요청인 경우 실패한다.")
	@Test
	void shouldRejectEventCreationForInvalidUser() {

		// given
		Random random = new Random();
		Long userId = random.nextLong();

		// 1. CreateBuddyEventUseCase를 발생 할 수 있는 Command 생성.
		CreateBuddyEventCommand command = CreateBuddyEventCommand.builder()
			.userId(userId)
			.eventStartDate(LocalDateTime.now().plusHours(1))
			.eventEndDate(LocalDateTime.now().plusHours(2))
			.participantCount(3)
			.eventConcepts(List.of(EventConcept.LEVEL_UP))
			.carShareYn(true)
			.comment("zzzz")
			.build();

		// 2. RequestMemberPort의 MemberStauts 상태 조회 결과를 실패로 만든다.
		Mockito.when(requestMemberPort.getMemberStatus(Mockito.any(Long.class)))
			.thenReturn(MemberStatus.builder().userid(11111L).isValid(false).build());

		// when, then
		assertThatThrownBy(() -> createBuddyEventUseCase.createBuddyEventV1(command))
			.isInstanceOf(RuntimeException.class)
			.hasMessage("비정상적인 사용자.");
	}

	@DisplayName("이미 모집 중인 버디 이벤트와 겹치는 시간의 이벤트를 생성하려고 하면 실패한다.")
	@Test
	void overlappingBuddyEventCreationShouldBeRejected() {
		//given

		//when

		//then

	}

}