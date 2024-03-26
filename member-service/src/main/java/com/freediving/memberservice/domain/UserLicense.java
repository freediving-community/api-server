package com.freediving.memberservice.domain;

import java.util.List;
import java.util.stream.Collectors;

import com.freediving.memberservice.adapter.out.persistence.UserLicenceJpaEntity;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/17
 * @Description    : 유저가 등록한 라이센스 정보를 저장하는 불변 도메인
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/17        sasca37       최초 생성
 */
public record UserLicence(Integer licenceLevel, String licenceImgUrl, Boolean confirmTF, Long confirmAdminId) {

	public static UserLicence fromJpaEntity(UserLicenceJpaEntity userLicenceJpaEntity) {
		return new UserLicence(userLicenceJpaEntity.getLicenceLevel(), userLicenceJpaEntity.getLicenceImgUrl(),
			userLicenceJpaEntity.getConfirmTF(), userLicenceJpaEntity.getConfirmAdminId());
	}

	public static List<UserLicence> fromJpaEntityList(List<UserLicenceJpaEntity> userLicenceJpaEntityList) {
		return userLicenceJpaEntityList.stream().map(UserLicence::fromJpaEntity).collect(Collectors.toList());
	}
}
