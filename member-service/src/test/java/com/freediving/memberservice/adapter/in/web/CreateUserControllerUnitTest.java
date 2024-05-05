package com.freediving.memberservice.adapter.in.web;

import static com.freediving.memberservice.fixture.UserFixture.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freediving.memberservice.adapter.in.web.dto.CreateUserRequest;
import com.freediving.memberservice.adapter.in.web.dto.CreateUserResponse;
import com.freediving.memberservice.application.port.in.CreateUserCommand;
import com.freediving.memberservice.application.port.in.CreateUserUseCase;
import com.freediving.memberservice.application.port.out.service.buddy.BuddyUseCase;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/26
 * @Description    : CreateUserController 단위 테스트
 * 	1. MVC LAYER 테스트를 위해 @WebMvcTest 를 사용한다. (@Service, @Component, @Repository 등 로드하지 않는다)
 * 	2. SecurityFilterChain 을 Mocking 하여 순수 Controller를 테스트한다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/26        sasca37       최초 생성
 */

@WebMvcTest(controllers = CreateUserController.class)
class CreateUserControllerUnitTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CreateUserUseCase createUserUseCase;
	@MockBean
	private BuddyUseCase buddyUseCase;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private SecurityFilterChain securityFilterChain;

	@Test
	@DisplayName("유저 회원가입 성공 테스트")
	void createUserSuccess() throws Exception {

		CreateUserRequest request = CreateUserRequest.builder()
			.email(VALID_EMAIL)
			.oauthType(VALID_OAUTH_TYPE)
			.providerId(PROVIDER_ID)
			.build();

		// Mocking
		CreateUserResponse mockUser = mock(CreateUserResponse.class);
		when(mockUser.getOauthType()).thenReturn(VALID_OAUTH_TYPE.name());

		when(createUserUseCase.createOrGetUser(any(CreateUserCommand.class)))
			.thenReturn(mockUser);

		mockMvc.perform(post("/v1/service/users/register")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsBytes(request))
		).andExpect(status().isOk());
	}

	@Test
	@DisplayName("유저 회원가입 실패 테스트 - Request DTO 유효성 검증 실패")
	void createUserFailByRequest() throws Exception {
		CreateUserRequest request = CreateUserRequest.builder().build();

		mockMvc.perform(post("/v1/service/users/register")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsBytes(request))
		).andExpect(status().isBadRequest());
	}
}