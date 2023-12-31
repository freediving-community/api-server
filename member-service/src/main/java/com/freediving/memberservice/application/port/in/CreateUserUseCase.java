package com.freediving.memberservice.application.port.in;

import com.freediving.common.config.annotation.UseCase;

@UseCase
public interface CreateUserUseCase {
	void createUser(CreateUserCommand command);
}
