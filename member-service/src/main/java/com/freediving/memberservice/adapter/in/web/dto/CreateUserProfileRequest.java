package com.freediving.memberservice.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/05/19
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/05/19        sasca37       최초 생성
 */

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Schema(description = "유저 프로필 정보 등록 DTO")
public class CreateUserProfileRequest {

	@Schema(description = "유저 프로필 이미지 URL", example = "https://d1pjflw6c3jt4r.cloudfront.net")
	private String profileImgUrl;

	@Size(min = 1, max = 16, message = "닉네임은 1자 이상 16자 이하여야 합니다.")
	@Schema(description = "유저 닉네임", example = "SASCA37")
	private String nickname;

	@Size(max = 400, message = "자기소개 글은 400글자 이하여야 합니다.")
	@Schema(description = "유저 자기소개 글", example = "안녕하세요.")
	private String content;

}
