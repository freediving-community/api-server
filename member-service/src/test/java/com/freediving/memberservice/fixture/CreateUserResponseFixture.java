package com.freediving.memberservice.fixture;

import com.freediving.memberservice.adapter.in.web.dto.CreateUserResponse;
import com.freediving.memberservice.application.port.in.CreateUserCommand;

/**
 * @Author         : sasca37
 * @Date           : 4/20/24
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 4/20/24        sasca37       최초 생성
 */
public class CreateUserResponseFixture {

	public static CreateUserResponse createUserResponseFromCommand(CreateUserCommand command) {
		return new CreateUserResponse(UserFixture.DEFAULT_ID, command.getEmail(), command.getProfileImgUrl(), null,
			command.getOauthType().name(), true, null);
	}
}
