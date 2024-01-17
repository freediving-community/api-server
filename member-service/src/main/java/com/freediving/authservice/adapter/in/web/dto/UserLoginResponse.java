package com.freediving.authservice.adapter.in.web.dto;

import com.freediving.authservice.domain.OauthType;
import com.freediving.authservice.domain.OauthUser;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/17
 * @Description    : 소셜 로그인 결과 정보를 반환하는 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/17        sasca37       최초 생성
 */

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserLoginResponse {
	private OauthType oauthType;
	private String email;
	private String profileImgUrl;

	private String accessToken;

	private String refreshToken;

	private Boolean isNewUser;

	public static UserLoginResponse from(OauthUser oauthUser) {
		return new UserLoginResponse(oauthUser.getOauthType(), oauthUser.getEmail(), oauthUser.getProfileImgUrl(),
			oauthUser.getAccessToken(), oauthUser.getRefreshToken(), oauthUser.getIsNewUser());
	}
}
