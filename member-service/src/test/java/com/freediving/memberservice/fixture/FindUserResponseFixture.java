package com.freediving.memberservice.fixture;

import com.freediving.memberservice.adapter.in.web.dto.FindUserResponse;
import com.freediving.memberservice.domain.User;

/**
 * @Author         : sasca37
 * @Date           : 2024/02/04
 * @Description    : User Detail 정보 조회를 위한 Fixture
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/02/04        sasca37       최초 생성
 */
public class FindUserResponseFixture {

	public static FindUserResponse fromUser(User user) {
		return FindUserResponse.builder()
			.userId(user.userId())
			.email(user.email())
			.profileImgUrl(user.profileImgUrl())
			.oauthType(user.oauthType().name())
			.roleLevel(user.roleLevel().getLevel())
			.build();
	}
}
