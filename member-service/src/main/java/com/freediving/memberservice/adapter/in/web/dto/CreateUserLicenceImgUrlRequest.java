package com.freediving.memberservice.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/28
 * @Description    : 유저 라이센스 이미지 생성 등록 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/28        sasca37       최초 생성
 */

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Schema(description = "유저 라이센스 이미지 URL 요청 DTO")
public class CreateUserLicenceImgUrlRequest {

	@Schema(description = "유저 라이센스 이미지 URL", example = "https://aws-s3.com")
	@NotNull(message = "라이센스 레벨은 필수 값입니다.")
	private String licenceImgUrl;
}
