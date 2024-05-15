package com.freediving.common.domain.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/03/25
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/03/25        sasca37       최초 생성
 */

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Builder
@Schema(description = "다이빙 별 라이센스 정보")
public class MemberLicenseInfo {

	private FreeDiving freeDiving;
	private ScubaDiving scubaDiving;
}
