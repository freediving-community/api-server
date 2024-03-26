package com.freediving.common.response.dto.member;

import com.freediving.common.domain.member.MemberLicenseInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author         : sasca37
 * @Date           : 2024/03/26
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/03/26        sasca37       최초 생성
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberFindUserResponse {
	private Long userId;

	private String profileImgUrl;

	private String nickname;

	private MemberLicenseInfo licenseInfo;
}
