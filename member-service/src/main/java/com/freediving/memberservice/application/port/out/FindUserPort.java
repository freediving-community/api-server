package com.freediving.memberservice.application.port.out;

import com.freediving.memberservice.domain.User;

public interface FindUserPort {

	User findUser(Long userId);
}
