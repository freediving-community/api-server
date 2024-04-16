package com.freediving.authservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.naver")
public record NaverOauthConfig(
	String localRedirectUri,
	String devRedirectUri,
	String prdRedirectUri,
	String clientId,
	String clientSecret,
	String state,
	String codeUri
) {
}
