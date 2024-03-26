package com.freediving.memberservice.adapter.in.web.dto;

import java.util.List;

import com.freediving.memberservice.domain.User;
import com.freediving.memberservice.domain.UserLicense;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @Author         : sasca37
 * @Date           : 2024/02/03
 * @Description    : 유저 조회 요청에 대한 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/02/03        sasca37       최초 생성
 */

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(description = "유저 정보 조회 요청 DTO")
public class FindUserResponse {

	@Schema(description = "유저 식별 키", example = "1")
	private Long userId;

	@Schema(description = "이메일", example = "sasca37@naver.com")
	private String email;

	@Schema(description = "프로필 이미지 URL", example = "https://d1pjflw6c3jt4r.cloudfront.net")
	private String profileImgUrl;

	@Schema(description = "닉네임", example = "초보다이버_00001")
	private String nickname;

	@Schema(description = "자기소개글", example = "안녕하세요")
	private String content;
	@Schema(description = "소셜 로그인 타입", example = "KAKAO")
	private String oauthType;

	@Schema(description = "다이빙 별 라이센스 정보")
	private LicenseInfo licenseInfo;

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/02/03
	 * @Param            : UserJpaEntity
	 * @Return           : 유저 조회에 필요한 정보를 담은 UserDto
	 * @Description      : UserJpaEntity 정보를 UserDto로 변환
	 */
	public static FindUserResponse from(User user) {
		List<UserLicense> userLicenseList = user.userLicenseList();
		LicenseInfo licenseInfo = LicenseInfo.createLicenseInfo(userLicenseList);
		return FindUserResponse.builder()
			.userId(user.userId())
			.email(user.email())
			.profileImgUrl(user.profileImgUrl())
			.oauthType(user.oauthType().name())
			.nickname(user.nickname())
			.content(user.content())
			.licenseInfo(licenseInfo)
			.build();
	}
}
