package com.freediving.memberservice.application.service;

import org.springframework.transaction.annotation.Transactional;

import com.freediving.common.config.annotation.UseCase;
import com.freediving.memberservice.application.port.in.FindUserCommand;
import com.freediving.memberservice.application.port.in.FindUserUseCase;
import com.freediving.memberservice.application.port.out.FindUserPort;
import com.freediving.memberservice.domain.User;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional
public class FindUserService implements FindUserUseCase {

	private final FindUserPort findUserPort;

	@Override
	public User findUser(FindUserCommand findUserCommand) {
		User user = findUserPort.findUser(findUserCommand.getUserId());
		// if (user == null)
		return user;
	}
}
