package com.freediving.memberservice.fixture;

import com.freediving.memberservice.application.port.in.CreateUserCommand;
import com.freediving.memberservice.domain.OauthType;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/27
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/27        sasca37       최초 생성
 */
public class CreateUserCommandFixture {

	public static CreateUserCommand createCommand(OauthType oauthType, String email, String profileImgUrl,
		String providerId) {
		return new CreateUserCommand(oauthType, email, profileImgUrl, providerId);
	}

}
