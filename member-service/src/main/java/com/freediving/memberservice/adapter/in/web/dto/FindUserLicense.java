package com.freediving.memberservice.adapter.in.web.dto;

import com.freediving.memberservice.adapter.out.persistence.UserJpaEntity;
import com.freediving.memberservice.adapter.out.persistence.UserLicenseJpaEntity;
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
public class FindUserLicense {

	@Schema(description = "유저 식별 키", example = "1")
	private Long userId;

	@Schema(description = "프로필 이미지 URL", example = "https://d1pjflw6c3jt4r.cloudfront.net")
	private String profileImgUrl;

	@Schema(description = "닉네임", example = "초보다이버_00001")
	private String nickname;

	@Schema(description = "라이센스 식별 값", example = "1")
	private Long licenseId;

	@Schema(description = "다이브 타입", example = "F")
	private String diveType;

	@Schema(description = "유저 라이센스 레벨 (null, 0 ~ 5)  null : 미입력, 0 : 자격증 없음, 1 : 1레벨,"
		+ "2 : 2레벨, 3 : 3레벨, 4 : 4레벨, 5 : 강사 ", example = "1")
	private Integer licenseLevel;

	@Schema(description = "유저 라이센스 이미지 URL", example = "https://d1pjflw6c3jt4r.cloudfront.net")
	private String licenseImgUrl;

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/02/03
	 * @Param            : UserJpaEntity
	 * @Return           : 유저 조회에 필요한 정보를 담은 UserDto
	 * @Description      : UserJpaEntity 정보를 UserDto로 변환
	 */
	public static FindUserLicense fromUserLicenseJpaEntity(UserLicenseJpaEntity userLicenseJpaEntity) {
		UserJpaEntity user = userLicenseJpaEntity.getUserJpaEntity();

		return FindUserLicense.builder()
			.userId(user.getUserId())
			.profileImgUrl(user.getProfileImgUrl())
			.nickname(user.getNickname())
			.licenseId(userLicenseJpaEntity.getId())
			.diveType(userLicenseJpaEntity.getDiveType().getCode())
			.licenseLevel(userLicenseJpaEntity.getLicenseLevel())
			.licenseImgUrl(userLicenseJpaEntity.getLicenseImgUrl())
			.build();
	}
}
