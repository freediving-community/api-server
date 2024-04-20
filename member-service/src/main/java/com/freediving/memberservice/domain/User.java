package com.freediving.memberservice.domain;

import java.util.List;

import com.freediving.memberservice.adapter.out.persistence.UserJpaEntity;
import com.freediving.memberservice.adapter.out.persistence.UserLicenseJpaEntity;

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
				   String nickname, String content, OauthType oauthType,
				   List<UserLicense> userLicenseList
) {

	public static User fromJpaEntityList(UserJpaEntity userJpaEntity, List<UserLicenseJpaEntity> userLicenceJpaList) {

		return new User(userJpaEntity.getUserId(), userJpaEntity.getEmail(), userJpaEntity.getProfileImgUrl(),
			userJpaEntity.getNickname(), userJpaEntity.getContent(), userJpaEntity.getOauthType(),
			UserLicense.fromJpaEntityList(userLicenceJpaList)
		);
	}

}
