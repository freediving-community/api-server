package com.freediving.memberservice.application.service;

import org.springframework.transaction.annotation.Transactional;

import com.freediving.common.config.annotation.UseCase;
import com.freediving.memberservice.application.port.in.CreateUserCommand;
import com.freediving.memberservice.application.port.in.CreateUserLicenceImgUrlCommand;
import com.freediving.memberservice.application.port.in.CreateUserLicenceLevelCommand;
import com.freediving.memberservice.application.port.in.CreateUserLicenceUseCase;
import com.freediving.memberservice.application.port.in.CreateUserUseCase;
import com.freediving.memberservice.application.port.out.CreateUserLicencePort;
import com.freediving.memberservice.application.port.out.CreateUserPort;
import com.freediving.memberservice.domain.User;

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
public class CreateUserService implements CreateUserUseCase, CreateUserLicenceUseCase {

	private final CreateUserPort createUserPort;
	private final CreateUserLicencePort createUserLicencePort;

	@Override
	public User createOrGetUser(CreateUserCommand command) {
		User user = createUserPort.createOrGetUser(command);
		return user;
	}

	@Override
	public void createUserLicenceLevel(Long userId, CreateUserLicenceLevelCommand command) {
		Integer licenceLevel = command.getLicenceLevel();
		createUserLicencePort.createUserLicenceLevel(userId, licenceLevel);
	}

	@Override
	public void createUserLicenceImgUrl(Long userId, CreateUserLicenceImgUrlCommand command) {
		String licenceImgUrl = command.getLicenceImgUrl();
		createUserLicencePort.createUserLicenceImgUrl(userId, licenceImgUrl);
	}
}
