package com.freediving.authservice.adapter.out.external.naver;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record NaverTokenResponse(
	String accessToken,
	String refreshToken,
	String tokenType,
	Integer expiresIn,
	String error,
	String errorDescription
) {
}
