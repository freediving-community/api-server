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

	@Schema(description = "프로필 이미지 URL", example = "https://aws-s3.com")
	private String profileImgUrl;

	@Schema(description = "닉네임", example = "버디킹")
	private String nickname;

	@Schema(description = "소셜 로그인 타입", example = "KAKAO")
	private String oauthType;

	@Schema(description = "유저 권한", example = "0")
	private Integer roleLevel;

	@Schema(description = "유저 권한 코드", example = "UNREGISTER")
	private String roleLevelCode;

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/01/17
	 * @Param            : User 도메인
	 * @Return           : 로그인에 필요한 정보를 담은 UserDto
	 * @Description      : User 도메인 정보를 UserDto로 변환
	 */
	public static CreateUserResponse from(User user) {
		return new CreateUserResponse(user.userId(), user.email(), user.profileImgUrl(), user.nickname(),
			user.oauthType().name(), user.roleLevel().getLevel(), user.roleLevel().name());
	}
}
