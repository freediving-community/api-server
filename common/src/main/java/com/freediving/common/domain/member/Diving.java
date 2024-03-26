package com.freediving.common.domain.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class Diving {
	@Schema(description = "유저 권한", example = "0")
	protected Integer roleLevel = RoleLevel.UNREGISTER.getLevel();

	@Schema(description = "유저 권한 코드", example = "UNREGISTER")
	protected String roleLevelCode = RoleLevel.UNREGISTER.name();

	@Schema(description = "유저 라이센스 레벨 (null, 0 ~ 5)  null : 미입력, 0 : 자격증 없음, 1 : 1레벨,"
		+ "2 : 2레벨, 3 : 3레벨, 4 : 4레벨, 5 : 강사 ", example = "1")
	protected Integer licenseLevel;

	@Schema(description = "유저 라이센스 이미지 URL", example = "https://d1pjflw6c3jt4r.cloudfront.net")
	protected String licenseImgUrl;

	@Schema(description = "유저 라이센스 관리자 승인 여부 (true, false)", example = "false")
	protected Boolean licenseValidTF;
}
