package com.freediving.memberservice.adapter.in.web.dto;

import com.freediving.memberservice.domain.OauthType;
import com.freediving.memberservice.valid.EnumValid;

import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/15
 * @Description    : 유저 생성 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/15        sasca37       최초 생성
 */

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class CreateUserRequest {

	@EnumValid(enumClass = OauthType.class, message = "소셜 타입 정보가 올바르지 않습니다.")
	private OauthType oauthType;

	@Email(message = "이메일 형식이 올바르지 않습니다.")
	private String email;

	private String profileImgUrl;
}
