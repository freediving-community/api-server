package com.freediving.memberservice.application.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.freediving.common.config.annotation.UseCase;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.memberservice.adapter.in.web.dto.CreateUserResponse;
import com.freediving.memberservice.application.port.in.CreateUserCommand;
import com.freediving.memberservice.application.port.in.CreateUserInfoCommand;
import com.freediving.memberservice.application.port.in.CreateUserInfoCommandV2;
import com.freediving.memberservice.application.port.in.CreateUserProfileCommand;
import com.freediving.memberservice.application.port.in.CreateUserUseCase;
import com.freediving.memberservice.application.port.in.CreateUserUseCaseV2;
import com.freediving.memberservice.application.port.out.CreateUserPort;
import com.freediving.memberservice.application.port.out.CreateUserPortV2;

import lombok.RequiredArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/17
 * @Description    : UseCase 정보를 기반으로 유저 정보를 생성 / 업데이트 하는 Service
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/17        sasca37       최초 생성
 */

@UseCase
@RequiredArgsConstructor
@Transactional
public class CreateUserService implements CreateUserUseCase, CreateUserUseCaseV2 {

	private final CreateUserPort createUserPort;
	private final CreateUserPortV2 createUserPortV2;

	@Override
	public CreateUserResponse createOrGetUser(CreateUserCommand command) {
		return createUserPort.createOrGetUser(command);
	}

	@Override
	public void createUserInfo(CreateUserInfoCommand command) {

		// 라이센스 레벨이 0이 아니면서 자격증 이미지 정보가 없은 경우 예외
		if (command.getLicenseLevel() != 0 && StringUtils.isEmpty(command.getLicenseImgUrl())) {
			throw new BuddyMeException(ServiceStatusCode.BAD_REQUEST, "자격증 이미지 정보는 필수입니다.");
		}
		createUserPort.createUserInfo(command);
	}

	@Override
	public void createUserProfile(CreateUserProfileCommand command) {
		createUserPort.createUserProfile(command);
	}

	@Override
	public void createUserInfoV2(CreateUserInfoCommandV2 command) {
		createUserPortV2.createUserInfoV2(command);
	}
}
