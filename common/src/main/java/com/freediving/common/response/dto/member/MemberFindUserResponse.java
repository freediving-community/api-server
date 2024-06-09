package com.freediving.common.response.dto.member;

import com.freediving.common.domain.member.MemberLicenseInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author         : sasca37
 * @Date           : 2024/03/26
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/03/26        sasca37       최초 생성
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "유저 정보 조회 응답 DTO")
public class MemberFindUserResponse {

	@Schema(description = "유저 식별 키", example = "1")
	private Long userId;

	@Schema(description = "유저 상태 코드", example = "ACTIVE")
	private String userStatus;

	@Schema(description = "프로필 이미지 URL", example = "https://d1pjflw6c3jt4r.cloudfront.net")
	private String profileImgUrl;

	@Schema(description = "닉네임", example = "초보다이버_00001")
	private String nickname;

	@Schema(description = "다이빙 별 라이센스 정보")
	private MemberLicenseInfo licenseInfo;
}
