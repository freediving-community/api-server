package com.freediving.memberservice.application.port.out;

import com.freediving.memberservice.adapter.in.web.dto.CreateUserResponse;
import com.freediving.memberservice.application.port.in.CreateUserCommand;
import com.freediving.memberservice.application.port.in.CreateUserInfoCommand;
import com.freediving.memberservice.application.port.in.CreateUserProfileCommand;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/17
 * @Description    : 유저 생성 / 업데이트 작업을 전달하기 위한 Port
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/17        sasca37       최초 생성
 */

public interface CreateUserPort {

	CreateUserResponse createOrGetUser(CreateUserCommand createUserCommand);

	void createUserInfo(CreateUserInfoCommand command);

	void createUserProfile(CreateUserProfileCommand command);
}
