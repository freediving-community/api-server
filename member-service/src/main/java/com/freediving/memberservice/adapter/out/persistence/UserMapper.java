package com.freediving.memberservice.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.freediving.memberservice.domain.User;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/17
 * @Description    : UserJpaEntity를 User 도메인으로 Convert
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/17        sasca37       최초 생성
 */

@Component
public class UserMapper {
	User fromUserJpaEntity(UserJpaEntity userJpaEntity) {
		/*
		return User.createSimpleUser(userJpaEntity.getId(), userJpaEntity.getOauthType(),
			userJpaEntity.getEmail(), userJpaEntity.getProfileImgUrl(),
			userJpaEntity.getUserTokenJpaEntity().getRefreshToken());
		 */
		return null;
	}
}
