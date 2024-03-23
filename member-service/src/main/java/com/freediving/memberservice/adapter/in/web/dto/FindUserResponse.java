package com.freediving.memberservice.adapter.in.web.dto;

import org.apache.commons.lang3.ObjectUtils;

import com.freediving.memberservice.domain.User;
import com.freediving.memberservice.domain.UserLicence;

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

	@Schema(description = "유저 권한", example = "0")
	private Integer roleLevel;

	@Schema(description = "유저 권한 코드", example = "UNREGISTER")
	private String roleLevelCode;

	@Schema(description = "유저 라이센스 레벨 (null, 0 ~ 5)  null : 미입력, 0 : 자격증 없음, 1 : 1레벨,"
		+ "2 : 2레벨, 3 : 3레벨, 4 : 4레벨, 5 : 강사 ", example = "1")
	private Integer licenceLevel;

	@Schema(description = "유저 라이센스 이미지 URL", example = "https://d1pjflw6c3jt4r.cloudfront.net")
	private String licenceImgUrl;

	@Schema(description = "유저 라이센스 관리자 승인 여부 (true, false)", example = "false")
	private Boolean licenceValidTF;

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/02/03
	 * @Param            : UserJpaEntity
	 * @Return           : 유저 조회에 필요한 정보를 담은 UserDto
	 * @Description      : UserJpaEntity 정보를 UserDto로 변환
	 */
	public static FindUserResponse from(User user) {
		UserLicence userLicence = user.userLicence();

		Integer licenceLevel = null;
		String licenceImgUrl = null;
		Boolean licenceValidTF = false;
		if (!ObjectUtils.isEmpty(userLicence)) {
			licenceLevel = userLicence.licenceLevel();
			licenceImgUrl = userLicence.licenceImgUrl();
			licenceValidTF = userLicence.confirmTF();
		}
		return FindUserResponse.builder()
			.userId(user.userId())
			.email(user.email())
			.profileImgUrl(user.profileImgUrl())
			.oauthType(user.oauthType().name())
			.nickname(user.nickname())
			.content(user.content())
			.roleLevel(user.roleLevel().getLevel())
			.roleLevelCode(user.roleLevel().name())
			.licenceLevel(licenceLevel)
			.licenceImgUrl(licenceImgUrl)
			.licenceValidTF(licenceValidTF)
			.build();
	}
}
