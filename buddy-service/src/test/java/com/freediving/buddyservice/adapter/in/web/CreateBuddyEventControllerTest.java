package com.freediving.buddyservice.adapter.in.web;

import java.time.LocalDateTime;
import java.util.List;

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
import com.freediving.buddyservice.application.port.in.CreateBuddyEventCommand;
import com.freediving.buddyservice.application.port.in.CreateBuddyEventUseCase;
import com.freediving.buddyservice.application.port.out.service.RequestMemberPort;
import com.freediving.buddyservice.common.ControllerDefendenciesConfig;
import com.freediving.buddyservice.common.enumeration.EventConcept;
import com.freediving.buddyservice.common.enumeration.EventStatus;
import com.freediving.buddyservice.domain.CreatedBuddyEvent;

@WebMvcTest(controllers = CreateBuddyEventController.class)
@ActiveProfiles("local")
class CreateBuddyEventControllerTest extends ControllerDefendenciesConfig {

	@MockBean
	private CreateBuddyEventUseCase createBuddyEventUseCase;

	@MockBean
	private RequestMemberPort requestMemberPort;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void tearDown() {

		Mockito.when(createBuddyEventUseCase.createBuddyEvent(Mockito.any(CreateBuddyEventCommand.class)))
			.thenReturn(CreatedBuddyEvent.builder()
				.eventId(1L)
				.userId(1L)
				.eventStartDate(LocalDateTime.of(2024, 01, 01, 10, 00, 00))
				.eventEndDate(LocalDateTime.of(2024, 01, 01, 14, 00, 00))
				.participantCount(11)
				.eventConcepts(List.of(EventConcept.LEVEL_UP))
				.status(EventStatus.RECRUITING)
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
			.participantCount(11)
			.eventConcepts(List.of(EventConcept.LEVEL_UP))
			.carShareYn(false)
			.comment("ㅋㅋㅋㅋ")
			.build();

		//when then
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/event")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.eventId").value(1))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.userId").value(1))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.eventStartDate").value("2024-01-01T10:00:00"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.eventEndDate").value("2024-01-01T14:00:00"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.participantCount").value(11))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.eventConcepts[0]").value("LEVEL_UP"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.carShareYn").value(false))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.status").value(EventStatus.RECRUITING.name()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.comment").value("ㅋㅋㅋㅋ"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.createdDate").value("2024-01-01T09:00:00"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.updatedDate").value("2024-01-01T09:00:00"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.msg").isEmpty());
	}

	@DisplayName("일정 시작 시간 필수 값 체크")
	@Test
	void eventStartTimeIsRequired() throws Exception {
		//given
		final CreateBuddyEventRequest request = CreateBuddyEventRequest.builder()
			.eventEndDate(LocalDateTime.now().plusHours(8))
			.participantCount(11)
			.eventConcepts(List.of(EventConcept.LEVEL_UP))
			.carShareYn(false)
			.comment("ㅋㅋㅋㅋ")
			.build();

		//when then
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/event")
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
			.eventConcepts(List.of(EventConcept.LEVEL_UP))
			.carShareYn(false)
			.comment("ㅋㅋㅋㅋ")
			.build();

		//when then
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/event")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400))
			.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("BAD_REQUEST(일정 종료 시간은 필수입니다.)"));
	}

}