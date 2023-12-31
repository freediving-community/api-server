package com.freediving.authservice.application.port.out;

import com.freediving.authservice.domain.OauthUser;

public interface OauthFeignPort extends OauthRedirectUrlPort {

	OauthUser fetch(String code);
}
