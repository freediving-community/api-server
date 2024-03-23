package com.freediving.memberservice.fixture;

import com.freediving.memberservice.adapter.out.persistence.UserJpaEntity;
import com.freediving.memberservice.domain.OauthType;

/**
 * @Author         : sasca37
 * @Date           : 2/28/24
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2/28/24        sasca37       최초 생성
 */
public class UserEntityFixture {

	public static UserJpaEntity createMockUser(Long userId, String email, String profileImgUrl, OauthType oauthType) {
		return UserJpaEntity.createMockUser()
			.userId(userId)
			.email(email)
			.profileImgUrl(profileImgUrl)
			.oauthType(oauthType)
			.role(RoleLevel.UNREGISTER)
			.build();
	}

}
