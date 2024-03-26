package com.freediving.memberservice.adapter.in.web.dto;

import com.freediving.memberservice.domain.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/16
 * @Description    : 유저 생성 요청에 대한 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/16        sasca37       최초 생성
 */

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "유저 생성 응답 DTO")
public class CreateUserResponse {

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

	@Schema(description = "라이센스 정보")
	private LicenseInfo licenseInfo;

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/01/17
	 * @Param            : User 도메인
	 * @Return           : 로그인에 필요한 정보를 담은 UserDto
	 * @Description      : User 도메인 정보를 UserDto로 변환
	 */
	public static CreateUserResponse from(User user) {

		return new CreateUserResponse(user.userId(), user.email(), user.profileImgUrl(), user.nickname(),
			user.oauthType().name(), LicenseInfo.createLicenseInfo(user.userLicenseList()));
	}
}
