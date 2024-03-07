package com.freediving.memberservice.application.port.in;

import static com.freediving.memberservice.fixture.CreateUserCommandFixture.*;
import static com.freediving.memberservice.fixture.UserFixture.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolationException;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/27
 * @Description    : CreateUserCommand 단위 테스트
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/27        sasca37       최초 생성
 */
class CreateUserCommandUnitTest {

	@Test
	@DisplayName("Command 전환 실패 테스트 - 이메일 정보가 올바르지 않은 경우")
	void commandConvertFailByEmail() {
		Assertions.assertThrows(ConstraintViolationException.class,
			() -> createCommand(VALID_OAUTH_TYPE, INVALID_EMAIL, OPTIONAL_PROFILE_IMG_URL));
	}

	@Test
	@DisplayName("Command 전환 실패 테스트 - Oauth 정보가 올바르지 않은 경우")
	void commandConvertFailByOauth() {
		Assertions.assertThrows(ConstraintViolationException.class,
			() -> createCommand(INVALID_OAUTH_TYPE, VALID_EMAIL, OPTIONAL_PROFILE_IMG_URL));
	}
}