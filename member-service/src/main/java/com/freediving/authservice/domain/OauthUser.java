package com.freediving.authservice.domain;

import com.freediving.authservice.adapter.out.external.OauthResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/18
 * @Description    : Oauth 정보를 저장하는 도메인
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/18        sasca37       최초 생성
 */

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OauthUser {

	private String userId;

	private OauthType oauthType;
	private String email;
	private String profileImgUrl;

	private Integer roleLevel;

	private String accessToken;

	private String refreshToken;

	public static OauthUser from(OauthResponse oauthResponse) {
		return new OauthUser(oauthResponse.oauthType(), oauthResponse.email(), oauthResponse.profileImgUrl());
	}

	private OauthUser(OauthType oauthType, String email, String profileImgUrl) {
		this.oauthType = oauthType;
		this.email = email;
		this.profileImgUrl = profileImgUrl;
	}

	public void updateTokens(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
