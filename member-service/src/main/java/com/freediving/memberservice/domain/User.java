package com.freediving.memberservice.domain;

import com.freediving.memberservice.adapter.out.persistence.UserJpaEntity;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/17
 * @Description    : User 정보를 저장하는 불변 도메인
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/17        sasca37       최초 생성
 */

public record User(Long userId, OauthType oauthType, String email, String profileImgUrl,
				   String refreshToken) {

	public static User fromJpaEntity(UserJpaEntity userJpaEntity) {
		return new User(userJpaEntity.getId(), userJpaEntity.getOauthType(), userJpaEntity.getEmail(),
			userJpaEntity.getProfileImgUrl(), userJpaEntity.getUserTokenJpaEntity().getRefreshToken());
	}

}
