package com.freediving.communityservice.adapter.out.dto.user;

import com.freediving.common.domain.member.FreeDiving;
import com.freediving.common.domain.member.MemberLicenseInfo;
import com.freediving.common.domain.member.ScubaDiving;

import lombok.Data;

@Data
public class UserInfo {
	private Long userId;
	private String userStatus;
	private String nickname;
	private String profileImgUrl;
	private MemberLicenseInfo memberLicenseInfo;

	public UserInfo(Long userId, String userStatus, String nickname, String profileImgUrl,
		MemberLicenseInfo memberLicenseInfo) {
		this.userId = userId;
		this.userStatus = userStatus;
		this.nickname = nickname;
		this.profileImgUrl = profileImgUrl;
		FreeDiving f = memberLicenseInfo.getFreeDiving();
		ScubaDiving s = memberLicenseInfo.getScubaDiving();
		this.memberLicenseInfo = MemberLicenseInfo.builder()
			.freeDiving(
				new FreeDiving(f.getOrgName(), f.getRoleLevel(), f.getRoleLevelCode(), f.getLicenseLevel(), null,
					f.getLicenseValidTF()))
			.scubaDiving(
				new ScubaDiving(s.getOrgName(), s.getRoleLevel(), s.getRoleLevelCode(), s.getLicenseLevel(), null,
					s.getLicenseValidTF()))
			.build();
	}
	// private Integer maxRoleLevel; scubaDiving 추가시 대소 비교하여 사용 Integer.max(user.getLicenseInfo().getFreeDiving().getRoleLevel(),user.getLicenseInfo().getScubaDiving().getRoleLevel())
	// private Integer freeDivingRoleLevel;
	// private Integer freeDivingLicenseLevel;
	// private Integer freeDivingLicenseValid;
}
