package com.freediving.memberservice.adapter.out.persistence;

import static com.freediving.memberservice.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/27
 * @Description    : UserJpaRepository 테스트
 * 1. @DataJpaTest를 사용하여 JPA 관련 구성만 로드한다.
 * 2. TestEntityManager를 주입받아서 영속성 컨텍스트를 테스트한다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/27        sasca37       최초 생성
 */

@DataJpaTest
class UserJpaRepositoryTest {

	@Autowired
	TestEntityManager entityManager;

	@Autowired
	UserJpaRepository userJpaRepository;

	@BeforeEach
	public void setUp() {
		UserJpaEntity user = UserJpaEntity.createSimpleUser(VALID_OAUTH_TYPE, VALID_EMAIL, OPTIONAL_PROFILE_IMG_URL,
			DEFAULT_ROLE_LEVEL);
		entityManager.persistAndFlush(user);
	}

	@AfterEach
	public void cleanUp() {
		entityManager.clear();
	}

	@Test
	@DisplayName("유저 조회 성공 테스트 - OauthType과 Email로 유저 조회")
	void findUserByOauthTypeAndEmailSuccess() {
		Optional<UserJpaEntity> findUser = userJpaRepository.findByOauthTypeAndEmail(VALID_OAUTH_TYPE, VALID_EMAIL);
		assertThat(findUser).isPresent();
		assertThat(findUser.get().getEmail()).isEqualTo(VALID_EMAIL);
		assertThat(findUser.get().getOauthType()).isEqualTo(VALID_OAUTH_TYPE);
		assertThat(findUser.get().getRoleLevel()).isEqualTo(DEFAULT_ROLE_LEVEL);
	}
}