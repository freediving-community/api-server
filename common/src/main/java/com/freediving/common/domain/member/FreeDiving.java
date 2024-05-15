package com.freediving.common.domain.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/03/24
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/03/24        sasca37       최초 생성
 */

@Getter
@NoArgsConstructor
@Schema(description = "프리다이빙 라이센스 정보")
public class FreeDiving extends Diving {

	@Schema(description = "권한 레벨", example = "0")
	private Integer roleLevel;

	@Schema(description = "권한 코드명", example = "UNREGISTER")
	private String roleLevelCode;

	@Schema(description = "자격증 레벨", example = "2")
	private Integer licenseLevel;

	@Schema(description = "라이센스 이미지 URL", example = "https://d1pjflw6c3jt4r.cloudfront.net")
	private String licenseImgUrl;

	@Schema(description = "라이센스 검증 여부(T/F)", example = "F")
	private Boolean licenseValidTF;

	public FreeDiving(Integer roleLevel, String roleLevelCode, Integer licenseLevel, String licenseImgUrl,
		Boolean licenseValidTF) {
		super(roleLevel, roleLevelCode, licenseLevel, licenseImgUrl, licenseValidTF);
	}
}
