package com.freediving.authservice.application.port.in;

import com.freediving.authservice.adapter.in.web.UserLoginResponse;
import com.freediving.common.config.annotation.UseCase;

@UseCase
public interface JwtTokenUseCase {

	void provideJwtToken(UserLoginResponse userLoginResponse);
}
