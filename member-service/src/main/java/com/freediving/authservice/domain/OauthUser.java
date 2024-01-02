package com.freediving.authservice.domain;

import com.freediving.authservice.adapter.out.external.OauthResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OauthUser {

	private OauthType oauthType;
	private String email;
	private String profileImgUrl;

	public static OauthUser from(OauthResponse oauthResponse) {
		return new OauthUser(oauthResponse.oauthType(), oauthResponse.email(), oauthResponse.profileImgUrl());
	}
}
