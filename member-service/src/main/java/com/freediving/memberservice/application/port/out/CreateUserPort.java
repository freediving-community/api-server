package com.freediving.memberservice.application.port.out;

import com.freediving.memberservice.domain.OauthType;

public interface CreateUserPort {

	void createUser(OauthType oauthType, String email, String profileImgUrl);
}
