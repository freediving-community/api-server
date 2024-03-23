package com.freediving.authservice.adapter.in.web.dto;

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

	@Schema(description = "유저 식별 키", example = "1")
	private Long userId;

	@Schema(description = "이메일", example = "sasca37@naver.com")
	private String email;

	@Schema(description = "프로필 이미지 URL", example = "https://d1pjflw6c3jt4r.cloudfront.net")
	private String profileImgUrl;

	@Schema(description = "닉네임", example = "초보다이버_00001")
	private String nickname;

	@Schema(description = "소셜 로그인 타입", example = "KAKAO")
	private String oauthType;

	@Schema(description = "유저 권한", example = "0")
	private Integer roleLevel;

	public static UserLoginResponse from(OauthUser oauthUser) {
		return new UserLoginResponse(Long.valueOf(oauthUser.getUserId()), oauthUser.getEmail(),
			oauthUser.getProfileImgUrl(), oauthUser.getNickname(), oauthUser.getOauthType().name(),
			oauthUser.getRoleLevel());
	}
}
