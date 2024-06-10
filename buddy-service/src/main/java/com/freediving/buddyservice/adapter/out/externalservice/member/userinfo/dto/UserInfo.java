package com.freediving.buddyservice.adapter.out.externalservice.member.userinfo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.freediving.buddyservice.config.enumerate.UserStatus;
import com.freediving.buddyservice.domain.query.common.UserInfoResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo {

	private Long userId;

	private String profileImgUrl;

	private String nickname;

	private UserStatus userStatus;
	private LicenseInfo licenseInfo;

	public UserInfoResponse toResponse() {
		return UserInfoResponse.builder()
			.userId(this.userId)
			.profileImgUrl(this.profileImgUrl)
			.nickname(this.nickname)
			.licenseInfo(this.licenseInfo)
			.build();
	}

}
