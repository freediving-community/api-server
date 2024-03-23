package com.freediving.memberservice.domain;

import java.util.Optional;

import com.freediving.common.domain.RoleLevel;
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

public record User(Long userId, String email, String profileImgUrl,
				   String nickname, String content, OauthType oauthType, RoleLevel roleLevel,
				   UserLicence userLicence
) {

	public static User fromJpaEntitySimple(UserJpaEntity userJpaEntity) {
		return new User(userJpaEntity.getUserId(), userJpaEntity.getEmail(), userJpaEntity.getProfileImgUrl(),
			userJpaEntity.getNickname(), userJpaEntity.getContent(), userJpaEntity.getOauthType(),
			userJpaEntity.getRole(),
			null
		);
	}

	public static User fromJpaEntityDetail(UserJpaEntity userJpaEntity) {
		UserLicence userLicence = Optional.ofNullable(userJpaEntity.getUserLicenceJpaEntity())
			.map(UserLicence::fromJpaEntity).orElse(null);

		return new User(userJpaEntity.getUserId(), userJpaEntity.getEmail(), userJpaEntity.getProfileImgUrl(),
			userJpaEntity.getNickname(), userJpaEntity.getContent(), userJpaEntity.getOauthType(),
			userJpaEntity.getRole(), userLicence
		);
	}
}
