package com.freediving.buddyservice.application.port.in;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import com.freediving.buddyservice.application.port.in.web.command.CreateBuddyEventCommand;

import jakarta.validation.ConstraintViolationException;

@ActiveProfiles("local")
class CreateBuddyEventCommandTest {

	@DisplayName("버디 이벤트 생성 Command 일정 시작 시간이 현재 시간보다 작은 경우 실패한다.")
	@Test
	void test1() {
		assertThatThrownBy(() -> gernerateCreateBuddyEventCommand(1L, LocalDateTime.now().minusHours(1),
			LocalDateTime.now().plusHours(1), 3, ""
		)).isInstanceOf(ConstraintViolationException.class)
			.hasMessage("eventStartDate: 일정 시작 시간은 현재 시간 이후여야 합니다.");

	}

	@DisplayName("버디 이벤트 생성 Command 최대 선택 가능한 인원수가 5명보다 높으면 실패한다..")
	@Test
	void test2() {
		assertThatThrownBy(() -> gernerateCreateBuddyEventCommand(1L, LocalDateTime.now().plusHours(1),
			LocalDateTime.now().plusHours(2), 6, ""
		)).isInstanceOf(ConstraintViolationException.class)
			.hasMessage("participantCount: 최대 모집 인원은 5명까지 가능합니다.");

	}

	private CreateBuddyEventCommand gernerateCreateBuddyEventCommand(
		Long userId,
		LocalDateTime eventStartDate,
		LocalDateTime eventEndDate,
		Integer participantCount,
		String comment
	) {
		return CreateBuddyEventCommand.builder()
			.userId(userId)
			.eventStartDate(eventStartDate)
			.eventEndDate(eventEndDate)
			.comment(comment)
			.participantCount(participantCount)
			.build();
	}

}