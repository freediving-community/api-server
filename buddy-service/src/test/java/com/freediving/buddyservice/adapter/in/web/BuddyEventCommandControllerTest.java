package com.freediving.buddyservice.adapter.in.web;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freediving.buddyservice.adapter.in.web.command.BuddyEventCommandController;
import com.freediving.buddyservice.adapter.in.web.command.dto.CreateBuddyEventRequest;
import com.freediving.buddyservice.application.port.in.web.command.CreateBuddyEventCommand;
import com.freediving.buddyservice.application.port.in.web.command.CreateBuddyEventUseCase;
import com.freediving.buddyservice.application.port.in.web.command.like.BuddyEventLikeToggleUseCase;
import com.freediving.buddyservice.common.ControllerDefendenciesConfig;
import com.freediving.buddyservice.config.enumerate.GenderType;
import com.freediving.buddyservice.domain.command.CreatedBuddyEventResponse;
import com.freediving.buddyservice.domain.enumeration.BuddyEventStatus;

@WebMvcTest(controllers = BuddyEventCommandController.class)
@ActiveProfiles("local")
class BuddyEventCommandControllerTest extends ControllerDefendenciesConfig {

	@MockBean
	private CreateBuddyEventUseCase createBuddyEventUseCase;

	@MockBean
	private BuddyEventLikeToggleUseCase buddyEventLikeToggleUseCase;
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void tearDown() {
		Mockito.when(createBuddyEventUseCase.createBuddyEvent(Mockito.any(CreateBuddyEventCommand.class)))
			.thenReturn(CreatedBuddyEventResponse.builder()
				.eventId(1L)
				.userId(1L)
				.eventStartDate(LocalDateTime.of(2024, 01, 01, 10, 00, 00))
				.eventEndDate(LocalDateTime.of(2024, 01, 01, 14, 00, 00))
				.participantCount(5)
				.status(BuddyEventStatus.RECRUITING)
				.freedivingLevel(0)
				.carShareYn(false)
				.comment("ㅋㅋㅋㅋ")
				.createdDate(LocalDateTime.of(2024, 01, 01, 9, 00, 00))
				.updatedDate(LocalDateTime.of(2024, 01, 01, 9, 00, 00))
				.build());
	}

	@DisplayName("새로운 버디 이벤트를 생성한다.")
	@Test
	void shouldCreateNewBuddyEventSuccessfully() throws Exception {

		//given
		final CreateBuddyEventRequest request = CreateBuddyEventRequest.builder()
			.eventStartDate(LocalDateTime.now().plusHours(2))
			.eventEndDate(LocalDateTime.now().plusHours(8))
			.participantCount(5)
			.genderType(GenderType.ALL)
			.carShareYn(false)
			.comment("ㅋㅋㅋㅋ")
			.build();

		//when then
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/event")
				.header("User-Id", 1L)
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk()); // todo 임시

	}

	@DisplayName("일정 시작 시간 필수 값 체크")
	@Test
	void eventStartTimeIsRequired() throws Exception {
		//given
		final CreateBuddyEventRequest request = CreateBuddyEventRequest.builder()
			.eventEndDate(LocalDateTime.now().plusHours(8))
			.participantCount(11)
			.carShareYn(false)
			.genderType(GenderType.ALL)
			.comment("ㅋㅋㅋㅋ")
			.build();

		//when then
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/event")
				.header("User-Id", 1L)
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400))
			.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("BAD_REQUEST(일정 시작 시간은 필수입니다.)"));
	}

	@DisplayName("일정 종료 시간 필수 값 체크")
	@Test
	void eventEndTimeIsRequired() throws Exception {
		//given
		final CreateBuddyEventRequest request = CreateBuddyEventRequest.builder()
			.eventStartDate(LocalDateTime.now().plusHours(8))
			.participantCount(11)
			.genderType(GenderType.ALL)
			.carShareYn(false)
			.comment("ㅋㅋㅋㅋ")
			.build();

		//when then
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/event")
				.header("User-Id", 1L)
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400))
			.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("BAD_REQUEST(일정 종료 시간은 필수입니다.)"));
	}

}