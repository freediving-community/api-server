package com.freediving.common.domain.member;

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
public class CommonLicenseInfo {
	private FreeDiving freeDiving;
	private ScubaDiving scubaDiving;
}
