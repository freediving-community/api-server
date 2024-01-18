package com.freediving.authservice.adapter.in.web.dto;

import com.freediving.authservice.domain.OauthType;
import com.freediving.authservice.domain.OauthUser;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "유저 로그인 응답 정보")
public class UserLoginResponse {

	@Schema(description = "소셜 로그인 타입", example = "KAKAO")
	private OauthType oauthType;

	@Schema(description = "이메일", example = "sasca37@naver.com")
	private String email;

	@Schema(description = "이메일", example = "https://aws-s3.com")
	private String profileImgUrl;

	@Schema(description = "액세스 토큰", example = "aaaaaaa.bbbbbbb.ccccccc")
	private String accessToken;

	@Schema(description = "리프레시 토큰", example = "aaaaaaa.bbbbbbb.ccccccc")
	private String refreshToken;

	@Schema(description = "최초 가입 여부", example = "true")
	private Boolean isNewUser;

	public static UserLoginResponse from(OauthUser oauthUser) {
		return new UserLoginResponse(oauthUser.getOauthType(), oauthUser.getEmail(), oauthUser.getProfileImgUrl(),
			oauthUser.getAccessToken(), oauthUser.getRefreshToken(), oauthUser.getIsNewUser());
	}
}
