package com.freediving.memberservice.adapter.in.web.dto;

import org.hibernate.validator.constraints.Range;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/28
 * @Description    : 유저 라이센스 레벨 생성 등록 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/28        sasca37       최초 생성
 */

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Schema(description = "유저 라이센스 레벨 요청 DTO")
public class CreateUserLicenceLevelRequest {

	@Schema(description = "유저 라이센스 레벨 (0 ~ 5)", example = "1")
	@Range(min = 0, max = 5, message = "자격증 레벨 정보는 0 ~ 5 사이여야 합니다.")
	private Integer licenceLevel;
}
