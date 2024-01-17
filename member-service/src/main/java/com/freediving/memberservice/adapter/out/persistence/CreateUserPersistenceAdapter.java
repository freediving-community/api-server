package com.freediving.memberservice.adapter.out.persistence;

import org.apache.commons.lang3.ObjectUtils;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.memberservice.application.port.in.CreateUserCommand;
import com.freediving.memberservice.application.port.out.CreateUserPort;
import com.freediving.memberservice.domain.OauthType;
import com.freediving.memberservice.domain.User;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserPersistenceAdapter implements CreateUserPort {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	@Override
	public User createUser(CreateUserCommand createUserCommand) {

		final OauthType oauthType = createUserCommand.getOauthType();
		final String email = createUserCommand.getEmail();
		final String profileImgUrl = createUserCommand.getProfileImgUrl();
		final String accessToken = createUserCommand.getAccessToken();
		final String refreshToken = createUserCommand.getRefreshToken();

		UserJpaEntity findUserJpaEntity = userRepository.findByOauthTypeAndEmail(oauthType, email);
		if (!ObjectUtils.isEmpty(findUserJpaEntity)) {
			// TODO : Throw Exception
		}
		UserJpaEntity userEntity = UserJpaEntity.createSimpleUser(oauthType, email, profileImgUrl);
		userRepository.save(userEntity);

		return User.createSimpleUser(userEntity.getId(), userEntity.getOauthType(),
			userEntity.getEmail(), userEntity.getProfileImgUrl());
	}

}
