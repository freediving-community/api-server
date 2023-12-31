package com.freediving.memberservice.application.port.in;

import com.freediving.memberservice.domain.User;

public interface FindUserUseCase {
	User findUser(FindUserCommand findUserCommand);
}
