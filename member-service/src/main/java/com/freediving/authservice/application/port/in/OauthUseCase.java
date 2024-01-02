package com.freediving.authservice.application.port.in;

import com.freediving.authservice.adapter.in.web.UserLoginResponse;
import com.freediving.authservice.domain.OauthType;
import com.freediving.common.config.annotation.UseCase;

@UseCase
public interface OauthUseCase {

	String provideOauthType(OauthType oauthType);

	UserLoginResponse login(OauthType oauthType, String code);
}
