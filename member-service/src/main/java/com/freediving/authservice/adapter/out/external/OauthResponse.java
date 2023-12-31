package com.freediving.authservice.adapter.out.external;

import com.freediving.authservice.domain.OauthType;

public record OauthResponse(OauthType oauthType, String email, String profileImgUrl) {

	public static OauthResponse of(OauthType oauthType, String email, String profileImgUrl) {
		return new OauthResponse(oauthType, email, profileImgUrl);
	}
}
