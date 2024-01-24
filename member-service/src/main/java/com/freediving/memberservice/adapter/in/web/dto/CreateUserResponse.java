package com.freediving.memberservice.adapter.in.web.dto;

import com.freediving.memberservice.domain.User;

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
public class CreateUserResponse {

	private Long userId;

	private String email;

	private String oauthType;

	private String profileImgUrl;

	private String refreshToken;

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/01/17
	 * @Param            : User 도메인
	 * @Return           : 로그인에 필요한 정보를 담은 UserDto
	 * @Description      : User 도메인 정보를 UserDto로 변환
	 */
	public static CreateUserResponse from(User user) {
		return new CreateUserResponse(user.userId(), user.email(), user.oauthType().name(), user.profileImgUrl(),
			user.refreshToken());
	}
}
