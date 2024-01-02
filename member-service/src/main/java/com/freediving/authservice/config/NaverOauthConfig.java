package com.freediving.authservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.naver")
public record NaverOauthConfig(
	String redirectUri,
	String clientId,
	String clientSecret,
	String state,
	String codeUri
) {
}
