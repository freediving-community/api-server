package com.freediving.buddyservice.adapter.in.web.v1;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freediving.buddyservice.application.port.in.CreateBuddyEventCommand;
import com.freediving.buddyservice.application.port.in.CreateBuddyEventUseCase;
import com.freediving.buddyservice.application.port.out.service.RequestMemberPort;
import com.freediving.buddyservice.common.enumeration.EventConcept;
import com.freediving.buddyservice.domain.CreatedBuddyEvent;

@WebMvcTest(controllers = CreateBuddyEventControllerV1.class)
class CreateBuddyEventControllerV1Test {

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

		Mockito.when(createBuddyEventUseCase.createBuddyEventV1(Mockito.any(CreateBuddyEventCommand.class)))
			.thenReturn(CreatedBuddyEvent.builder()
				.eventId(1L)
				.userId(1L)
				.eventStartDate(LocalDateTime.now().plusHours(4))
				.eventEndDate(LocalDateTime.now().plusHours(11))
				.participantCount(11)
				.eventConcepts(List.of(EventConcept.LEVEL_UP))
				.carShareYn(false)
				.comment("")
				.createdDate(LocalDateTime.now())
				.updatedDate(LocalDateTime.now())
				.build());

	}

	@DisplayName("새로운 버디 이벤트를 생성한다.")
	@Test
	void shouldCreateNewBuddyEventSuccessfully() throws Exception {
		//given
		CreateBuddyEventRequestV1 request = CreateBuddyEventRequestV1.builder()
			.eventStartDate(LocalDateTime.now().plusHours(2))
			.eventEndDate(LocalDateTime.now().plusHours(8))
			.participantCount(11)
			.eventConcepts(List.of(EventConcept.LEVEL_UP))
			.carShareYn(false)
			.comment("ㅋㅋㅋㅋ")
			.build();

		//when then
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/event/")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk());
		// TODO : 응답 규격 정해지면 응답 규격 검사 추가 필요.

	}

}