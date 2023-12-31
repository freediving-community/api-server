package com.freediving.memberservice.adapter.out.persistence;

import org.springframework.util.ObjectUtils;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.memberservice.application.port.out.CreateUserPort;
import com.freediving.memberservice.application.port.out.FindUserPort;
import com.freediving.memberservice.domain.OauthType;
import com.freediving.memberservice.domain.User;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserPersistenceAdapter implements CreateUserPort, FindUserPort {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	@Override
	public void createUser(OauthType oauthType, String email, String profileImgUrl) {

		UserJpaEntity findUserJpaEntity = userRepository.findByOauthTypeAndEmail(oauthType, email);
		if (ObjectUtils.isEmpty(findUserJpaEntity)) {
			UserJpaEntity userEntity = new UserJpaEntity(oauthType, email, profileImgUrl);
			userRepository.save(userEntity);
		}
	}

	@Override
	public User findUser(Long userId) {
		UserJpaEntity userJpaEntity = userRepository.findById(userId).orElse(null);
		if (ObjectUtils.isEmpty(userJpaEntity)) {
			return null;
		}
		return userMapper.fromUserJpaEntity(userJpaEntity);
	}
}
