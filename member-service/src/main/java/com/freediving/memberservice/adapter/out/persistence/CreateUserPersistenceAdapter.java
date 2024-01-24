package com.freediving.memberservice.adapter.out.persistence;

import org.apache.commons.lang3.ObjectUtils;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.memberservice.application.port.in.CreateUserCommand;
import com.freediving.memberservice.application.port.out.CreateUserPort;
import com.freediving.memberservice.domain.OauthType;
import com.freediving.memberservice.domain.User;

import lombok.RequiredArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/17
 * @Description    : 가입 여부를 확인하고 유저 생성 및 JWT 정보 업데이트
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/17        sasca37       최초 생성
 */

@PersistenceAdapter
@RequiredArgsConstructor
public class CreateUserPersistenceAdapter implements CreateUserPort {
	private final UserJpaRepository userJpaRepository;
	private final UserTokenJpaRepository userTokenJpaRepository;

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/01/17
	 * @Param            : CreateUserCommand
	 * @Return           : User 도메인
	 * @Description      : 최초 로그인인 경우 회원가입 / JWT 저장, 기존 가입자인 경우 JWT 업데이트
	 */
	@Override
	public User createOrUpdateUser(CreateUserCommand createUserCommand) {
		final OauthType oauthType = createUserCommand.getOauthType();
		final String email = createUserCommand.getEmail();

		UserJpaEntity userJpaEntity = userJpaRepository.findByOauthTypeAndEmail(oauthType, email).orElse(null);

		if (ObjectUtils.isEmpty(userJpaEntity)) {
			UserTokenJpaEntity userTokenJpaEntity = createUserTokenEntity(createUserCommand.getRefreshToken());
			userJpaEntity = createUserEntity(oauthType, email, createUserCommand.getProfileImgUrl(),
				userTokenJpaEntity);
			userJpaRepository.save(userJpaEntity);
		} else {
			updateRefreshToken(userJpaEntity, createUserCommand.getRefreshToken());
		}
		return User.fromJpaEntity(userJpaEntity);
	}

	private UserTokenJpaEntity createUserTokenEntity(String refreshToken) {
		return UserTokenJpaEntity.createToken(refreshToken);
	}

	private UserJpaEntity createUserEntity(OauthType oauthType, String email, String profileImgUrl,
		UserTokenJpaEntity userTokenJpaEntity) {
		UserJpaEntity userEntity = UserJpaEntity.createSimpleUser(oauthType, email, profileImgUrl);
		userEntity.updateToken(userTokenJpaEntity);
		return userEntity;
	}

	private void updateRefreshToken(UserJpaEntity userJpaEntity, String refreshToken) {
		UserTokenJpaEntity userTokenJpaEntity = userTokenJpaRepository.findById(
			userJpaEntity.getUserTokenJpaEntity().getId()).orElseThrow();
		userTokenJpaEntity.updateRefreshToken(refreshToken);
	}
}
