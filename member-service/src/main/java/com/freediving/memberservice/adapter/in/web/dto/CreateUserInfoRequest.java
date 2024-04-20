package com.freediving.memberservice.adapter.in.web.dto;

import java.util.List;

import org.hibernate.validator.constraints.Range;

import com.freediving.memberservice.domain.DiveType;
import com.freediving.memberservice.valid.EnumValid;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/03/23
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/03/23        sasca37       최초 생성
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Schema(description = "유저 회원 정보 등록 DTO")
public class CreateUserInfoRequest {

	@Schema(description = "유저 라이센스 종류 (프리다이빙 F, 스쿠버다이빙 S)", example = "F")
	@EnumValid(enumClass = DiveType.class)
	private DiveType diveType;

	@Schema(description = "유저 라이센스 레벨 (0 ~ 5)", example = "1")
	@Range(min = 0, max = 5, message = "자격증 레벨 정보는 0 ~ 5 사이여야 합니다.")
	private Integer licenseLevel;

	@Schema(description = "유저 라이센스 이미지 URL", example = "https://d1pjflw6c3jt4r.cloudfront.net")
	private String licenseImgUrl;

	@Size(min = 1, max = 2, message = "다이빙 풀 정보는 1개 이상 2개 이하여야 합니다.")
	@ArraySchema(schema = @Schema(type = "array", example = "1"))
	private List<String> poolList;

	@Size(min = 1, max = 2, message = "다이빙 컨셉 정보는 1개 이상 2개 이하여야 합니다.")
	@ArraySchema(schema = @Schema(type = "array", example = "1"))
	private List<String> conceptList;

	@Schema(description = "유저 프로필 이미지 URL", example = "https://d1pjflw6c3jt4r.cloudfront.net")
	private String profileImgUrl;

	@Size(min = 1, max = 16, message = "닉네임은 1자 이상 16자 이하여야 합니다.")
	@Schema(description = "유저 닉네임", example = "SASCA37")
	private String nickname;

	@Size(max = 400, message = "자기소개 글은 400글자 이하여야 합니다.")
	@Schema(description = "유저 자기소개 글", example = "안녕하세요.")
	private String content;

}
