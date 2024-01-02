package com.freediving.memberservice.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.freediving.memberservice.domain.User;

@Component
public class UserMapper {

	User fromUserJpaEntity(UserJpaEntity userJpaEntity) {
		return User.createUser(userJpaEntity.getUserId(), userJpaEntity.getOauthType(), userJpaEntity.getEmail(),
			userJpaEntity.getProfileImgUrl());
	}

}
