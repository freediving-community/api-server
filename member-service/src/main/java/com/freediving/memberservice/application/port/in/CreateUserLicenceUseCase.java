package com.freediving.memberservice.application.port.in;

import com.freediving.common.config.annotation.UseCase;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/17
 * @Description    : Command 객체를 기반으로 유저 생성 인입을 위한 UseCase
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/17        sasca37       최초 생성
 */

@UseCase
public interface CreateUserLicenceUseCase {
	void createUserLicenceLevel(Long userId, CreateUserLicenceLevelCommand command);

	void createUserLicenceImgUrl(Long userId, CreateUserLicenceImgUrlCommand command);
}
