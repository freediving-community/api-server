package com.freediving.memberservice.adapter.in.web.dto;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.freediving.memberservice.domain.DiveType;
import com.freediving.memberservice.domain.UserLicense;

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
public class LicenseInfo {

	private FreeDiving freeDiving;

	private ScubaDiving scubaDiving;

	public static LicenseInfo createLicenseInfo(List<UserLicense> userLicenseList) {
		FreeDiving freeDiving = new FreeDiving();
		ScubaDiving scubaDiving = new ScubaDiving();
		if (!CollectionUtils.isEmpty(userLicenseList)) {
			for (int i = 0; i < userLicenseList.size(); i++) {
				UserLicense userLicense = userLicenseList.get(i);
				if (userLicense.diveType().equals(DiveType.FREE_DIVE)) {
					freeDiving = new FreeDiving(userLicense.roleLevel().getLevel(), userLicense.roleLevel().name()
						, userLicense.licenseLevel(), userLicense.licenseImgUrl(), userLicense.confirmTF());
				} else {
					scubaDiving = new ScubaDiving(userLicense.roleLevel().getLevel(), userLicense.roleLevel().name()
						, userLicense.licenseLevel(), userLicense.licenseImgUrl(), userLicense.confirmTF());
				}
			}
		}
		return new LicenseInfo(freeDiving, scubaDiving);
	}
}
