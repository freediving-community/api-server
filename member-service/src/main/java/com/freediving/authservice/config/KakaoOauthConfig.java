package com.freediving.authservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.kakao")
public record KakaoOauthConfig(
	String clientId,
	String redirectUri,
	String clientSecret,
	String[] scope,

	String codeUri
) {
}
