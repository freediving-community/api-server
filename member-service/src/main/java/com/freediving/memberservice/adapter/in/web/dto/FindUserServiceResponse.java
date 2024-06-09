package com.freediving.memberservice.adapter.in.web.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.freediving.memberservice.domain.User;
import com.freediving.memberservice.domain.UserLicense;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @Author         : sasca37
 * @Date           : 2024/02/26
 * @Description    : 유저 조회 요청에 대한 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/02/26        sasca37       최초 생성
 */

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class FindUserServiceResponse {

	private Long userId;

	private String userStatus;

	private String profileImgUrl;

	private String nickname;

	private LicenseInfo licenseInfo;

	public static List<FindUserServiceResponse> of(List<User> findUserList, Boolean profileImgTF) {
		return findUserList.stream().map(e -> of(e, profileImgTF)).collect(Collectors.toList());
	}

	private static FindUserServiceResponse of(User user, Boolean profileImgTF) {
		List<UserLicense> userLicenseList = user.userLicenseList();
		LicenseInfo licenseInfo = LicenseInfo.createLicenseInfo(userLicenseList);
		return FindUserServiceResponse.builder()
			.userId(user.userId())
			.userStatus(user.userStatus())
			.profileImgUrl(profileImgTF == true ? user.profileImgUrl() : null)
			.nickname(user.nickname())
			.licenseInfo(licenseInfo)
			.build();
	}
}
