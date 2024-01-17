package com.freediving.memberservice.application.port.out;

import com.freediving.memberservice.application.port.in.CreateUserCommand;
import com.freediving.memberservice.domain.User;

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

	User createOrUpdateUser(CreateUserCommand createUserCommand);
}
