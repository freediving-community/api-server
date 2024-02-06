package com.freediving.memberservice.domain;

import org.apache.commons.lang3.ObjectUtils;

import com.freediving.memberservice.adapter.out.persistence.UserJpaEntity;
import com.freediving.memberservice.adapter.out.persistence.UserLicenceJpaEntity;

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
				   String nickname, OauthType oauthType, RoleLevel roleLevel,
				   UserLicence userLicence
) {

	public static User fromJpaEntitySimple(UserJpaEntity userJpaEntity) {
		return new User(userJpaEntity.getUserId(), userJpaEntity.getEmail(), userJpaEntity.getProfileImgUrl(),
			userJpaEntity.getNickname(), userJpaEntity.getOauthType(), userJpaEntity.getRole(),
			null
		);
	}

	public static User fromJpaEntityDetail(UserJpaEntity userJpaEntity) {
		UserLicenceJpaEntity licenceJpaEntity = userJpaEntity.getUserLicenceJpaEntity();
		UserLicence userLicence = null;
		if (!ObjectUtils.isEmpty(licenceJpaEntity)) {
			userLicence = UserLicence.fromJpaEntity(licenceJpaEntity);
		}

		return new User(userJpaEntity.getUserId(), userJpaEntity.getEmail(), userJpaEntity.getProfileImgUrl(),
			userJpaEntity.getNickname(), userJpaEntity.getOauthType(), userJpaEntity.getRole(), userLicence
		);
	}

}
