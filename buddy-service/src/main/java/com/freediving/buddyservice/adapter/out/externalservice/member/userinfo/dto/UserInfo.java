package com.freediving.buddyservice.adapter.out.externalservice.member.userinfo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo {

	private Long userId;

	private String profileImgUrl;

	private String nickname;
	private LicenseInfo licenseInfo;

}
