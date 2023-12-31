package com.freediving.authservice.adapter.out.external.google;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GoogleTokenResponse(
	String accessToken,
	Integer expiresIn,
	String tokenType,
	String scope,
	String refreshToken
) {
}
