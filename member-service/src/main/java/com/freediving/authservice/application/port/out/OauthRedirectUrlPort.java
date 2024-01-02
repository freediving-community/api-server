package com.freediving.authservice.application.port.out;

import com.freediving.authservice.domain.OauthType;

public interface OauthRedirectUrlPort {

	OauthType getOauthType();

	String createRequestUrl();

	default boolean canRequest(OauthType oauthType) {
		return getOauthType().equals(oauthType);
	}
}
