package com.freediving.authservice.domain;

import com.freediving.authservice.adapter.out.external.OauthResponse;
import com.freediving.common.domain.member.MemberLicenseInfo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@ToString
public class OauthUser {

	private String userId;
	private String email;
	private String profileImgUrl;

	private String nickname;

	private OauthType oauthType;

	private MemberLicenseInfo licenseInfo;

	private Boolean firstJoinTF = false;
	private String accessToken;
	private String refreshToken;

	private String providerId;

	public static OauthUser from(OauthResponse oauthResponse) {
		return new OauthUser(oauthResponse.oauthType(), oauthResponse.email(), oauthResponse.profileImgUrl(),
			oauthResponse.providerId());
	}

	private OauthUser(OauthType oauthType, String email, String profileImgUrl, String providerId) {
		this.oauthType = oauthType;
		this.email = email;
		this.profileImgUrl = profileImgUrl;
		this.providerId = providerId;
	}

	public void updateTokens(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
