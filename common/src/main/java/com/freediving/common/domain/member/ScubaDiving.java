package com.freediving.common.domain.member;

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
public class ScubaDiving extends Diving {
	public ScubaDiving(String orgName, Integer roleLevel, String roleLevelCode, Integer licenseLevel, String licenseImgUrl,
		Boolean licenseValidTF) {
		super(orgName, roleLevel, roleLevelCode, licenseLevel, licenseImgUrl, licenseValidTF);
	}
}
