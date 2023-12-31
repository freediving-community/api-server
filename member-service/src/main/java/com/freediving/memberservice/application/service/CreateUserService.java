package com.freediving.memberservice.application.service;

import org.springframework.transaction.annotation.Transactional;

import com.freediving.common.config.annotation.UseCase;
import com.freediving.memberservice.application.port.in.CreateUserCommand;
import com.freediving.memberservice.application.port.in.CreateUserUseCase;
import com.freediving.memberservice.application.port.out.CreateUserPort;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional
public class CreateUserService implements CreateUserUseCase {

	private final CreateUserPort createUserPort;

	@Override
	public void createUser(CreateUserCommand command) {
		createUserPort.createUser(command.getOauthType(), command.getEmail(), command.getProfileImgUrl());
	}
}
