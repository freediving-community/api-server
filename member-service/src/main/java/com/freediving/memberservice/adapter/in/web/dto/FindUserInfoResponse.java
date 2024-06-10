package com.freediving.memberservice.adapter.in.web.dto;

import java.util.List;

import com.freediving.memberservice.adapter.out.dto.UserReviewResponse;
import com.freediving.memberservice.adapter.out.dto.UserStoryResponse;
import com.freediving.memberservice.domain.User;
import com.freediving.memberservice.domain.UserLicense;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/06/09
 * @Description    : 다이버 정보 조회 요청에 대한 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/06/09        sasca37       최초 생성
 */

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Schema(description = "다이버 정보 조회 응답 DTO")
public class FindUserInfoResponse {

	@Schema(description = "유저 식별 키", example = "1")
	private Long userId;

	@Schema(description = "프로필 이미지 URL", example = "https://d1pjflw6c3jt4r.cloudfront.net")
	private String profileImgUrl;

	@Schema(description = "닉네임", example = "초보다이버_00001")
	private String nickname;

	@Schema(description = "자기소개글", example = "안녕하세요")
	private String content;

	@Schema(description = "다이빙 별 라이센스 정보")
	private LicenseInfo licenseInfo;

	@Schema(description = "자주가는 다이빙 풀 정보", example = "PARADIVE")
	private List<String> poolList;

	@Schema(description = "관심있는 컨셉 정보", example = "FUN")
	private List<String> conceptList;

	@Schema(description = "스토리")
	private UserStoryResponse story;

	@Schema(description = "받은 후기")
	private UserReviewResponse review;
	/**
	 * @Author           : sasca37
	 * @Date             : 2024/02/03
	 * @Param            : UserJpaEntity
	 * @Return           : 유저 조회에 필요한 정보를 담은 UserDto
	 * @Description      : UserJpaEntity 정보를 UserDto로 변환
	 */
	public static FindUserInfoResponse from(User user) {
		List<UserLicense> userLicenseList = user.userLicenseList();
		LicenseInfo licenseInfo = LicenseInfo.createLicenseInfo(userLicenseList);
		return FindUserInfoResponse.builder()
			.userId(user.userId())
			.profileImgUrl(user.profileImgUrl())
			.nickname(user.nickname())
			.content(user.content())
			.licenseInfo(licenseInfo)
			.poolList(user.poolList())
			.conceptList(user.conceptList())
			.build();
	}
}
