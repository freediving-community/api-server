package com.freediving.communityservice.adapter.out.dto.user;

import com.freediving.common.domain.member.MemberLicenseInfo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserInfo {
	private String nickname;
	private String profileImgUrl;
	private MemberLicenseInfo licenseInfo;
	// private Integer maxRoleLevel; scubaDiving 추가시 대소 비교하여 사용 Integer.max(user.getLicenseInfo().getFreeDiving().getRoleLevel(),user.getLicenseInfo().getScubaDiving().getRoleLevel())
	// private Integer freeDivingRoleLevel;
	// private Integer freeDivingLicenseLevel;
	// private Integer freeDivingLicenseValid;
}
