package com.freediving.buddyservice.adapter.out.externalservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindUser {

	private Long userId;

	private String profileImgUrl;

	private String nickname;
	private LicenseInfo licenseInfo;

}
