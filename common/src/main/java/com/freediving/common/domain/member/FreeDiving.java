package com.freediving.memberservice.adapter.in.web.dto;

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
public class FreeDiving extends Diving {
	public FreeDiving(Integer roleLevel, String roleLevelCode, Integer licenseLevel, String licenseImgUrl,
		Boolean licenseValidTF) {
		super(roleLevel, roleLevelCode, licenseLevel, licenseImgUrl, licenseValidTF);
	}
}
