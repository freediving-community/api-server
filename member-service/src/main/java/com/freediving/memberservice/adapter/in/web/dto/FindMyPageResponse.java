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
 * @Description    : 마이페이지 조회 요청에 대한 응답 DTO
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
public class FindMyPageResponse {

	@Schema(description = "유저 식별 키", example = "1")
	private Long userId;

	@Schema(description = "프로필 이미지 URL", example = "https://d1pjflw6c3jt4r.cloudfront.net")
	private String profileImgUrl;

	@Schema(description = "닉네임", example = "초보다이버_00001")
	private String nickname;

	@Schema(description = "소셜 로그인 타입", example = "KAKAO")
	private String oauthType;

	@Schema(description = "다이빙 별 라이센스 정보")
	private LicenseInfo licenseInfo;

	@Schema(description = "관심 목록 갯수", example = "10")
	private Integer likeCnt;

	@Schema(description = "내가 쓴 댓글 갯수", example = "5")
	private Integer commentCnt;

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/06/09
	 * @Return           : 유저 조회에 필요한 정보를 담은 UserDto
	 * @Description      : UserJpaEntity 정보를 UserDto로 변환
	 */
	public static FindMyPageResponse from(User user, Integer likeCnt, Integer commentCnt) {
		List<UserLicense> userLicenseList = user.userLicenseList();
		LicenseInfo licenseInfo = LicenseInfo.createLicenseInfo(userLicenseList);
		return FindMyPageResponse.builder()
			.userId(user.userId())
			.profileImgUrl(user.profileImgUrl())
			.nickname(user.nickname())
			.oauthType(user.oauthType().name())
			.licenseInfo(licenseInfo)
			.likeCnt(likeCnt)
			.commentCnt(commentCnt)
			.build();
	}
}
