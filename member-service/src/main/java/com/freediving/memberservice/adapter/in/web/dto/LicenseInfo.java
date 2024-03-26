package com.freediving.memberservice.adapter.in.web.dto;

import java.util.List;
import java.util.Optional;

import com.freediving.common.domain.member.FreeDiving;
import com.freediving.common.domain.member.ScubaDiving;
import com.freediving.memberservice.domain.DiveType;
import com.freediving.memberservice.domain.UserLicense;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class LicenseInfo {

	private FreeDiving freeDiving;

	private ScubaDiving scubaDiving;

	public static LicenseInfo createLicenseInfo(List<UserLicense> userLicenseList) {
		FreeDiving freeDiving = null;
		ScubaDiving scubaDiving = null;

		for (UserLicense userLicense : userLicenseList) {
			if (DiveType.FREE_DIVE.equals(userLicense.diveType())) {
				freeDiving = new FreeDiving(userLicense.roleLevel().getLevel(), userLicense.roleLevel().name(),
					userLicense.licenseLevel(), userLicense.licenseImgUrl(), userLicense.confirmTF());
			} else if (DiveType.SCUBA_DIVE.equals(userLicense.diveType())) {
				scubaDiving = new ScubaDiving(userLicense.roleLevel().getLevel(), userLicense.roleLevel().name(),
					userLicense.licenseLevel(), userLicense.licenseImgUrl(), userLicense.confirmTF());
			}
		}
		return new LicenseInfo(Optional.ofNullable(freeDiving).orElse(new FreeDiving()),
			Optional.ofNullable(scubaDiving).orElse(new ScubaDiving()));
	}
}
