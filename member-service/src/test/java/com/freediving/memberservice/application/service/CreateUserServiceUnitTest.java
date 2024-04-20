package com.freediving.memberservice.application.service;

import static com.freediving.memberservice.fixture.CreateUserCommandFixture.*;
import static com.freediving.memberservice.fixture.UserFixture.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.freediving.memberservice.adapter.in.web.dto.CreateUserResponse;
import com.freediving.memberservice.application.port.in.CreateUserCommand;
import com.freediving.memberservice.application.port.out.CreateUserPort;
import com.freediving.memberservice.fixture.CreateUserResponseFixture;

import jakarta.validation.ConstraintViolationException;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/27
 * @Description    : CreateUserService 단위 테스트
 * 1. MockitoExtentions를 사용하여 순수 서비스 테스트
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/27        sasca37       최초 생성
 */

@ExtendWith(MockitoExtension.class)
class CreateUserServiceUnitTest {

	@Mock
	private CreateUserPort createUserPort;

	@InjectMocks
	private CreateUserService createUserService;

	@Test
	@DisplayName("유저 회원가입 정상 동작 테스트")
	void createOrGetUserSuccess() {
		CreateUserCommand command = createCommand(VALID_OAUTH_TYPE, VALID_EMAIL, OPTIONAL_PROFILE_IMG_URL);

		CreateUserResponse expectedUser = CreateUserResponseFixture.createUserResponseFromCommand(command);

		when(createUserPort.createOrGetUser(command)).thenReturn(expectedUser);

		CreateUserResponse resultUser = createUserService.createOrGetUser(command);

		assertEquals(expectedUser, resultUser);
	}

	@Test
	@DisplayName("유저 회원가입 실패 테스트 - Command 유효성 검증 실패 - 이메일 정보가 올바르지 않을 때")
	void createOrGetUserFailByCommandInvalidEmail() {

		assertThrows(ConstraintViolationException.class, () -> {
			CreateUserCommand.builder()
				.oauthType(VALID_OAUTH_TYPE)
				.email(INVALID_EMAIL)
				.build();
		});
	}

	@Test
	@DisplayName("유저 회원가입 실패 테스트 - Command 유효성 검증 실패 - 소셜 정보가 올바르지 않을 때")
	void createOrGetUserFailByCommandInvalidSocial() {

		assertThrows(ConstraintViolationException.class, () -> {
			CreateUserCommand.builder()
				.oauthType(INVALID_OAUTH_TYPE)
				.email(VALID_EMAIL)
				.build();
		});
	}
}