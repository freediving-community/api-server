package com.freediving.memberservice.adapter.in.web.dto;

import org.hibernate.validator.constraints.Range;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/06/09
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/06/09        sasca37       최초 생성
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Schema(description = "유저 회원 정보 등록 DTO")
public class UpdateUserLicenseStatusRequest {

	@Schema(description = "라이센스 식별 값", example = "1")
	@NotNull(message = "라이센스 식별 값은 필수 값입니다.")
	private Long licenseId;

	@Schema(description = "유저 라이센스 레벨 (1 ~ 5)", example = "1")
	@Range(min = 1, max = 5, message = "자격증 레벨 정보는 1 ~ 5 사이여야 합니다.")
	private Integer licenseLevel;

	@Schema(description = "유저 라이센스 레벨 (1 ~ 5)", example = "AIDA")
	@NotBlank(message = "단체명은 필수 값입니다.")
	private String orgName;

	@Schema(description = "라이센스 심사 승인 여부 (T/F)", example = "true")
	@NotNull(message = "승인 여부 값은 필수 값입니다.")
	private Boolean confirmTF;

	@Size(max = 400, message = "거절 시 거절 사유 내용은 필수 값입니다.")
	@Schema(description = "거절 내용", example = "라이센스 이미지가 올바르지 않습니다.")
	private String rejectContent;

}
