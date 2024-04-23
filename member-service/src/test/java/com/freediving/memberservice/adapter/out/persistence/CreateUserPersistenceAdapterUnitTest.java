package com.freediving.memberservice.adapter.out.persistence;

import static com.freediving.memberservice.fixture.CreateUserCommandFixture.*;
import static com.freediving.memberservice.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.freediving.memberservice.adapter.in.web.dto.CreateUserResponse;
import com.freediving.memberservice.application.port.in.CreateUserCommand;
import com.freediving.memberservice.domain.OauthType;
import com.freediving.memberservice.fixture.UserEntityFixture;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/27
 * @Description    : CreateUserPersistenceAdapter 단위 테스트
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/27        sasca37       최초 생성
 */

@ExtendWith(MockitoExtension.class)
class CreateUserPersistenceAdapterUnitTest {

	@Mock
	private UserJpaRepository userJpaRepository;

	@Mock
	private UserLicenseJpaRepository userLicenseJpaRepository;

	@InjectMocks
	private CreateUserPersistenceAdapter createUserPersistenceAdapter;

	@Test
	@DisplayName("유저 생성 성공 테스트")
	void createUserSuccess() {
		CreateUserCommand command = createCommand(VALID_OAUTH_TYPE, VALID_EMAIL, OPTIONAL_PROFILE_IMG_URL);

		when(userJpaRepository.findByOauthTypeAndEmail(any(OauthType.class), anyString())).thenReturn(
			Optional.empty());

		UserJpaEntity mockedUserJpaEntity = UserEntityFixture.createMockUser(1L, command.getEmail(),
			command.getProfileImgUrl(), command.getOauthType());

		when(userJpaRepository.save(any(UserJpaEntity.class))).thenReturn(mockedUserJpaEntity);

		CreateUserResponse user = createUserPersistenceAdapter.createOrGetUser(command);

		assertThat(user).isNotNull();
		assertThat(user.getNickname()).isNotNull();
		assertThat(user.getEmail()).isEqualTo(VALID_EMAIL);
		assertThat(user.getOauthType()).isEqualTo(VALID_OAUTH_TYPE.name());
		verify(userJpaRepository, times(1)).save(any(UserJpaEntity.class));
	}

	@Test
	@DisplayName("유저 조회 성공 테스트")
	void getUserSuccess() {
		CreateUserCommand command = createCommand(VALID_OAUTH_TYPE, VALID_EMAIL, OPTIONAL_PROFILE_IMG_URL);
		UserJpaEntity existingUser = UserJpaEntity.createSimpleUser(VALID_OAUTH_TYPE, VALID_EMAIL,
			OPTIONAL_PROFILE_IMG_URL);

		when(userJpaRepository.findByOauthTypeAndEmail(any(OauthType.class), anyString())).thenReturn(
			Optional.of(existingUser));

		CreateUserResponse user = createUserPersistenceAdapter.createOrGetUser(command);

		assertThat(user).isNotNull();
		assertThat(user.getEmail()).isEqualTo(VALID_EMAIL);
		assertThat(user.getOauthType()).isEqualTo(VALID_OAUTH_TYPE.name());

		verify(userJpaRepository, times(0)).save(any(UserJpaEntity.class));
	}
}