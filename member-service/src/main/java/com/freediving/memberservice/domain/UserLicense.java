package com.freediving.memberservice.domain;

import java.util.List;
import java.util.stream.Collectors;

import com.freediving.common.domain.member.RoleLevel;
import com.freediving.memberservice.adapter.out.persistence.UserLicenseJpaEntity;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/17
 * @Description    : 유저가 등록한 라이센스 정보를 저장하는 불변 도메인
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/17        sasca37       최초 생성
 */
public record UserLicense(DiveType diveType, RoleLevel roleLevel, Integer licenseLevel, String licenseImgUrl,
						  Boolean confirmTF,
						  Long confirmAdminId) {

	public static UserLicense fromJpaEntity(UserLicenseJpaEntity userLicenseJpaEntity) {
		return new UserLicense(userLicenseJpaEntity.getDiveType(), userLicenseJpaEntity.getRole(),
			userLicenseJpaEntity.getLicenseLevel(),
			userLicenseJpaEntity.getLicenseImgUrl(), userLicenseJpaEntity.getConfirmTF(),
			userLicenseJpaEntity.getConfirmAdminId());
	}

	public static List<UserLicense> fromJpaEntityList(List<UserLicenseJpaEntity> userLicenseJpaEntityList) {
		return userLicenseJpaEntityList.stream().map(UserLicense::fromJpaEntity).collect(Collectors.toList());
	}
}
