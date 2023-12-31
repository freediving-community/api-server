package com.freediving.authservice.adapter.out.external.google;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GoogleInfoResponse(
	Long sub,
	String name,
	String givenName,
	String familyName,
	String picture,
	String email,
	String emailVerified,
	String locale
) {
}
