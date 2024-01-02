package com.freediving.authservice.adapter.in.web;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.freediving.authservice.domain.OauthType;
import com.freediving.authservice.domain.OauthUser;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
public class UserLoginResponse {
	private OauthType oauthType;
	private String email;
	private String profileImgUrl;

	private String accessToken;

	private String refreshToken;

	public void updateTokens(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public UserLoginResponse(OauthType oauthType, String email, String profileImgUrl) {
		this.oauthType = oauthType;
		this.email = email;
		this.profileImgUrl = profileImgUrl;
	}

	public static UserLoginResponse from(OauthUser oauthUser) {
		return new UserLoginResponse(oauthUser.getOauthType(), oauthUser.getEmail(), oauthUser.getProfileImgUrl());
	}
}
