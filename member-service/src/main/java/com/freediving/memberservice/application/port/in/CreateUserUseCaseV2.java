package com.freediving.memberservice.application.port.in;

import com.freediving.common.config.annotation.UseCase;

/**
 * @Author         : sasca37
 * @Date           : 2024/05/20
 * @Description    : Command 객체를 기반으로 유저 생성 인입을 위한 UseCase
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/05/20        sasca37       최초 생성
 */

@UseCase
public interface CreateUserUseCaseV2 {

	void createUserInfoV2(CreateUserInfoCommandV2 command);

}
